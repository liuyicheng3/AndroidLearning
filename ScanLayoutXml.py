#-*- coding: UTF-8 -*-
import os
import sys
from xml.dom.minidom import parse
import xml.dom.minidom
from xml.dom.minidom import Element
from collections import *

allNodes =[]
class ElementItem:
    def __init__(self,nodeName,viewId,clickAble):
        self.nodeName = nodeName
        self.viewId = viewId
        self.clickAble = clickAble

    def __repr__(self):
        return "%s : %s" % (self.nodeName,self.viewId)

def scanCurrentNode(node):
    if isinstance(node,Element):
        if node.hasAttribute('android:id'):
            rawAttr = node.getAttribute('android:id')
            if rawAttr.find(u"@+id/")>=0:
                allNodes.append(ElementItem(node.nodeName,rawAttr.split(u'@+id/')[1],node.hasAttribute('tools:tag')))

        if node.childNodes.length > 0:
            children = node.childNodes
            for item in children:
                scanCurrentNode(item)

def insertMutiLine(orignLines,insetPos,tobeInserLines):
    for (index,line) in enumerate(tobeInserLines):
        orignLines.insert(insetPos+index,line+"\n")

def scanLayoutXml(xmlName,outPutFile,rootViewName,clickListner):
    xmlPath = os.path.join(os.getcwd(),'app/src/main/res/layout',xmlName)
    if not outPutFile.endswith("temp.txt"):
        outPutFile =  os.path.join(os.getcwd(),'app/src/main/java',outPutFile)
    # xmlPath = os.path.join(os.getcwd(),xmlName)
    print xmlPath

    dic ={}

    # 使用minidom解析器打开 XML 文档
    DOMTree = xml.dom.minidom.parse(xmlPath)
    collection = DOMTree.documentElement
    scanCurrentNode(collection)

    print "View Count: %d " %len(allNodes)

    initViewFileds=[]
    initViewFileds.append("\tprivate void initViews(){")
    count = 0
    for item in  allNodes:
        if dic.has_key(item.nodeName):
            dic.get(item.nodeName).append(item.viewId)
        else:
            # itemIdArr=[item.viewId]
            dic[item.nodeName]=[item.viewId]
        # print  "%s = (%s)%s.findViewById(R.id.%s);" % (item.viewId,item.nodeName,rootViewName,item.viewId)
        initViewFileds.append("\t\t%s = (%s)%s.findViewById(R.id.%s);" % (item.viewId,item.nodeName,rootViewName,item.viewId))
        if item.clickAble:
            # print  "%s.setOnClickListener(%s)"% (item.viewId,clickListner)
            initViewFileds.append("\t\t%s.setOnClickListener(%s);"% (item.viewId,clickListner))
        count+=1
        if  count%5 == 0:
            initViewFileds.append("")
    initViewFileds.append("\t}\n")

    count = 0
    declareFileds=["\n"]
    for (k, v) in dic.items():
        sameTypesIds=''
        for idx, val in enumerate(v):
            sameTypesIds = sameTypesIds +val
            if idx < len(v)-1:
                sameTypesIds+=','
        # print  "private %s  %s;" % (k,sameTypesIds)
        declareFileds.append("\tprivate %s  %s;" % (k,sameTypesIds))
        count+=1
        if  count%5 == 0:
            declareFileds.append("")
    declareFileds.append("")

    hasClickableItem = False
    clickListnerFileds=["\n"]
    clickListnerFileds.append("\tpublic void onClick(View v) {")
    clickListnerFileds.append("\t\tswitch (v.getId()){")
    for item in allNodes:
        if item.clickAble:
            clickListnerFileds.append("\t\t\tcase R.id.%s:{"%item.viewId)
            clickListnerFileds.append("\t\t\t\t")
            clickListnerFileds.append("\t\t\t\tbreak;")
            clickListnerFileds.append("\t\t\t}")
            hasClickableItem = True
    clickListnerFileds.append("\t\t}\n")
    clickListnerFileds.append("\t}\n")
    clickListnerFileds.append("\n")

    if os.path.exists(outPutFile):
        fp = file(outPutFile)
        lines = []
        insertDelarePos = -1
        insertInitViewPos = -1
        for (index,line) in enumerate(fp): # 内置的迭代器, 效率很高
            lines.append(line)
            if ("onCreate(Bundle savedInstanceState)" in line) and insertDelarePos<0 :
                insertDelarePos = index-1
            elif insertDelarePos>=0 and insertInitViewPos<0 and line.strip().startswith("}"):
                insertInitViewPos = index
        fp.close()
        if insertInitViewPos>=0:
            insertInitViewPos+=1

        if insertInitViewPos == -1:
            for (index,line) in enumerate(lines): # 内置的迭代器, 效率很高
                if "{" in line :
                    insertDelarePos = index+1
                    insertInitViewPos = insertDelarePos+1;
                    break

        if insertInitViewPos == -1:
            insertDelarePos = 0
            insertInitViewPos = insertDelarePos+1;

        print "insertDeclarePos:%d  insertInitViewPos:%d"%(insertDelarePos,insertInitViewPos)

        if hasClickableItem:
            lines.insert(insertInitViewPos,'\n'.join(clickListnerFileds))
        lines.insert(insertInitViewPos,'\n'.join(initViewFileds))
        lines.insert(insertDelarePos,'\n'.join(declareFileds))

        fp = file(outPutFile, 'w')
        fp.writelines(lines)
        fp.close()
        print "done"
    else:
        print "outPutFile： %s " %outPutFile
        lines = []
        if hasClickableItem:
            lines.insert(0,'\n'.join(clickListnerFileds))
        lines.insert(0,'\n'.join(initViewFileds))
        lines.insert(0,'\n'.join(declareFileds))

        fp = file(outPutFile, 'w')
        fp.writelines(lines)
        fp.close()
        print "done"



# scanLayoutXml("activity_phone_call","com.bibi.chat.ui.base.dialog.PickCreateTypeDialog","mActivity","this")
# scanLayoutXml("activity_phone_call","temp.txt","mActivity","this")

# 可以直接通过 python SetBuildType.py activity_phone_call  com.bibi.chat.ui.base.dialog.PickCreateTypeDialog  mActivity this
# 第一参数是扫描的 布局文件的名称
# 第二参数是 生成的数据文件（可选）
# 第三个参数 是rootView的名称（可选）
# 第四个参数  是clickListner的名称（可选）

if len(sys.argv)<2:
    print "Error  请至少输入一个参数 需要扫描的布局文件"
else:
    srcLayoutFile = str(sys.argv[1])
    if not srcLayoutFile.endswith(".xml"):
        srcLayoutFile+=".xml"
    if len(sys.argv)>2:
        outPutFile = sys.argv[2]
        if '.' in outPutFile:
            pathArr=outPutFile.split(".")
            outPutFile= "/".join(pathArr)+".java"
        else:
            outPutFile =outPutFile+".java"
    else:
        outPutFile = 'temp.txt'
    ownerView = sys.argv[3] if len(sys.argv)>3 else 'mActivity'
    clickListner = sys.argv[4] if len(sys.argv)>4 else "this"
    print "ScanTarget:%s Output:%s RootView:%s ClickListner:%s " %(srcLayoutFile,outPutFile,ownerView,clickListner)
    scanLayoutXml(srcLayoutFile,outPutFile,ownerView,clickListner)











