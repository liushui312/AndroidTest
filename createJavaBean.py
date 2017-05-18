#coding=utf-8
import os,shutil,httplib,re,json,types  
SRC_ROOT_PATH = 'src/com/yealink/uc/logic'
DATA_DIR_PATH = SRC_ROOT_PATH +'/data'
REQUEST_DIR_PATH = SRC_ROOT_PATH +'/request'
RESPONESE_DIR_PATH = SRC_ROOT_PATH +'/response'
DATA_PACKAGE_NAME = 'com.yealink.uc.logic.data'
RESPONESE_PACKAGE_NAME = 'com.yealink.uc.logic.response'
REQUEST_PACKAGE_NAME = 'com.yealink.uc.logic.request'
JSON_DOC_PATH = 'jsonDoc.txt'
    
class JavaTemplate:
    CLASS_TEMPELATE = 'package {package};\n\n'\
    +'import com.yealink.uc.logic.common.Model; \n'\
    +'{topModule}\n'\
    +'public class {clzName} extends Model{{\n'\
    +'{content}\n'\
    +'}}'
    
    CHILD_CLASS_TEMPELATE= '\n{blank}public static class {clzName} extends Model{{\n'\
                         + '{content}\n'\
                         + '{blank}}}'
    
    PRIVATE_MEMBER ='    {blank}private {attrType} {attrName};\n'
    PRIVATE_GENERICITY_MEMBER = '    {blank}private {attrType}<{genType}> {attrName};\n'
    PRIVATE_METHOD_MEMBER ='    {blank}private {attrType} {attrName} = "{attrValue}";\n'
    PUBLIC_STATIC_FINAL_STRING_MEMBER = '    {blank}public static final String {attrName} = "{attrValue}";\n'
    
    PUBLIC_MEMBER_GET_METHOD = '    {blank}public {attrType} get{firstUpCaseName}() {{\n'\
                             + '        {blank}return {attrName};\n'\
                             + '    {blank}}}\n'
    
    PUBLIC_MEMBER_SET_METHOD = '    {blank}public void set{firstUpCaseName}({attrType} {attrName}) {{\n'\
                             + '        {blank}this.{attrName} = {attrName};\n'\
                             + '    {blank}}}\n'
                             
    PUBLIC_GENERICITY_MEMBER_GET_METHOD = '    {blank}public {attrType}<{genType}> get{firstUpCaseName}() {{\n'\
                                        + '        {blank}return {attrName};\n'\
                                        + '    {blank}}}\n'
    PUBLIC_GENERICITY_MEMBER_SET_METHOD = '    {blank}public void set{firstUpCaseName}({attrType}<{genType}> {attrName}) {{\n'\
                                        + '        {blank}this.{attrName} = {attrName};\n'\
                                        + '    {blank}}}\n'
class ExternalType:
    DATETIME_TYPE_NAME = 'DateTime'
    DATETIME_PACKAGE_PATH = 'com.yealink.uc.common.DateTime;'
    DATETIME_TAG = '$DateTime'
    
class Clz:
    def __init__(self):
        self.package = ""
        self.name = ""
        self.attrs = []
        self.child_cls_list = []
        self.isContainDate = False
        self.isContainList = False
        self.blank = 0
        
    def getPackage(self):
        return self.package
    def setPackage(self,package):
        self.package = package
    def getName(self):
        return self.name
    def setName(self,name):
        self.name = name
    def getAttrs(self):
        return self.attrs
    def putAttr(self,attr):
        self.attrs.append(attr)
    def getChildClsList(self):
        return self.child_cls_list
    def putChildCls(self,clz):
        self.child_cls_list.append(clz)
    def setBlank(self,blank):
        self.blank = blank
    def getBlank(self):
        return self.blank
    def getBlankStr(self):
        i = 0
        blankStr = ''
        while (i<self.blank):
            blankStr += '    '
            i +=1 
        return blankStr
    def getIsContainDate(self):
        for attr in self.attrs:
            if attr.getType() == ExternalType.DATETIME_TAG:
                return True
        for childClz in self.child_cls_list:
            if isinstance(childClz,Clz):
                if childClz.getIsContainDate() :
                    return True
            else :
                if childClz.getGenClz().getIsContainDate():
                    return True
        return False
    def getIsContainList(self):
        for childClz in self.child_cls_list:
            if isinstance(childClz,Clz):
                if childClz.getIsContainList() : return True
            else :
                return True
        for attr in self.attrs:
            if attr.getIsArr():
                return True
        return False

class ArrClzWrapper:
    def __init__(self,clz):
        self.next = clz
        self.blank = 0
    def nextIsArr(self):
        if isinstance(self.next,ArrClzWrapper):
            return True
        return False
    def getGenClz(self):
        if self.nextIsArr() :
            return self.next.getGenClz()
        else :
            return self.next
    def getNext(self):
        return self.next
    def setBlank(self,blank):
        self.blank = blank
    def getBlank(self):
        return self.blank
class Attr:
    def __init__(self,name,type,value):
        self.name = name
        self.type = type
        self.value = value
        self.isArr = False
        self.genType = None
    def setName(self,name):
        self.name = name
    def getName(self):
        return self.name
    def setType(self,type):
        self.type = type
    def getType(self):
        return self.type
    def setGenType(self,type):
        self.genType = type
    def getGenType(self):
        return self.genType
    def setValue(self,value):
        self.value = value
    def getValue(self):
        return self.value
    def getIsArr(self):
        return self.isArr
    def setIsArr(self,isArr):
        self.isArr = isArr
    @staticmethod
    def isDateType(json):
        if len(json) == 6 and json.__contains__('S') > 0 \
        and json.__contains__('d') >0 and json.__contains__('h') > 0 \
        and json.__contains__('min') and json.__contains__('mth') > 0 \
        and json.__contains__('y') > 0:
            return True
        else :
            return False
    
    @staticmethod
    def convertType(type,attrName):
        if type == ExternalType.DATETIME_TAG:
            return ExternalType.DATETIME_TYPE_NAME
        elif type is types.IntType:
            return 'int'
        elif type is types.BooleanType:
            return 'boolean'
        elif type is types.StringType:
            return 'String'
        elif type is types.DictType:
            return attrName
        elif type is types.ListType:
            return 'ArrayList'
        elif type is types.LongType:
            return 'long'
        elif type is types.FloatType:
            return 'float'
        elif type is types.UnicodeType:
            return 'String'
        elif type is types.NoneType:
            return 'String'

class CreateUtils :
    configureJson = {}
    notifyList = []
    methodList = []
    #带'_'字符串转驼峰命名格式
    @staticmethod
    def formatNameNotify(str):
        items = str.split('_')
        ret = ''
        for item in items:
            ret += item.title()
        return ret
    #头部大写
    @staticmethod
    def formatNameMethod(str):
        return str[0].upper() + str[1:]
    #头部小写
    @staticmethod
    def formatNameFirstLower(str):
        return str[0].lower() + str[1:]
    #泛型类型语句创建
    @staticmethod
    def createGenMember(genStr,clz):
        if clz.nextIsArr() :
            return CreateUtils.createGenMember('ArrayList<'+genStr+'>',clz.getNext())
        else :
            return str.format(genStr,clz.getGenClz().getName())
    #根据json对象创建Python的Clz类型对象
    @staticmethod
    def createClass(clz,json):
        #列表单项是列表时迭代，会进入的入口
        if type(json) is types.ListType:
            #如果还是列表继续使用ArrClzWrapper包裹一层
            return ArrClzWrapper(CreateUtils.createClass(clz,json[0]))
        for item in json:

            if item == None or item.split() == '' or item[0].isupper():
                raise Exception("Attr name:" + clz.getName() + "." + item +" is not correct")
            #成员名是method时做特殊处理
            if item == 'method':
                clz.getAttrs().append(Attr(item,type(json[item]),json[item]))
            #成员是对象
            elif type(json[item]) is types.DictType:
                if Attr.isDateType(json[item]):
                    clz.getAttrs().append(Attr(item,ExternalType.DATETIME_TAG,None))
                else:
                    childClz = Clz()
                    childClz.setBlank(clz.getBlank()+1)
                    childClz.setName(CreateUtils.formatNameMethod(item))
                    clz.putChildCls(CreateUtils.createClass(childClz, json[item]))
            #成员是列表
            elif type(json[item]) is types.ListType:
                if type(json[item][0]) is types.ListType:
                    #列表单项是列表
                    arrChildLeftClz = Clz()
                    arrChildLeftClz.setBlank(clz.getBlank()+1)
                    arrChildLeftClz.setName(CreateUtils.formatNameMethod(item))
                    arrChildLeftClz = ArrClzWrapper(CreateUtils.createClass(arrChildLeftClz, json[item][0]));
                    clz.putChildCls(arrChildLeftClz)
                elif type(json[item][0]) is types.DictType:
                    #列表单项是对象
                    arrChildLeftClz = Clz()
                    arrChildLeftClz.setBlank(clz.getBlank()+1)
                    arrChildLeftClz.setName(CreateUtils.formatNameMethod(item))
                    arrChildLeftClz = CreateUtils.createClass(arrChildLeftClz, json[item][0])
                    arrChildLeftClz = ArrClzWrapper(arrChildLeftClz);
                    clz.putChildCls(arrChildLeftClz)
                else :
                    #列表单项是基础类型
                    attr = Attr(item,type(json[item]),None)
                    attr.setIsArr(True)
                    attr.setGenType(type(json[item][0]))
                    clz.putAttr(attr)
            #成员是字符串
            elif (type(json[item]) is types.StringType or type(json[item]) is types.UnicodeType) and json[item] is not None and json[item].strip() != '':
                if json[item] == 'int' or json[item] == 'uint':
                    clz.putAttr(Attr(item,types.IntType,json[item]))
                elif json[item] == 'int64' or json[item] == 'ulong' or json[item] == 'uint64':
                    clz.putAttr(Attr(item,types.LongType,json[item]))
                elif json[item] == 'double':
                    clz.putAttr(Attr(item,types.FloatType,json[item]))
                else :
                    clz.putAttr(Attr(item,type(json[item]),json[item]))
            else:
            #成员是其他类型
                clz.putAttr(Attr(item,type(json[item]),None))
        return clz
    #根据python的Clz类型对象创建Java类类型成员
    @staticmethod
    def createJavaChildClass(clz):
        javaContent = ''
        #创建常量
        for attr in clz.getAttrs():
            if attr.getValue() is not None:
                finalItems = attr.getValue().split(',')
                for item in finalItems:
                    javaContent += JavaTemplate.PUBLIC_STATIC_FINAL_STRING_MEMBER.format(blank = clz.getBlankStr(),attrName = attr.getName().upper() +"_" + item.replace(r'::','_').upper(),attrValue = item)
        #创建基础类型变量
        for attr in clz.getAttrs():
            if attr.getIsArr():
                javaContent += JavaTemplate.PRIVATE_GENERICITY_MEMBER.format(blank = clz.getBlankStr(),genType = Attr.convertType(attr.getGenType(),None),attrType = Attr.convertType(attr.getType(),attr.getName()),attrName = attr.getName())
            else :
                javaContent += JavaTemplate.PRIVATE_MEMBER.format(blank = clz.getBlankStr(),attrType = Attr.convertType(attr.getType(),attr.getName()),attrName = attr.getName())
        #创建对象类型变量
        for childClz in clz.getChildClsList():
            if isinstance(childClz,Clz):
                javaContent+=JavaTemplate.PRIVATE_MEMBER.format(blank = clz.getBlankStr(),attrType = childClz.getName(),attrName = CreateUtils.formatNameFirstLower(childClz.getName()))
            else :
                genTypeStr = CreateUtils.createGenMember('ArrayList<{0}>',childClz)
                javaContent += JavaTemplate.PRIVATE_MEMBER.format(blank = clz.getBlankStr(),attrType = genTypeStr,attrName = CreateUtils.formatNameFirstLower(childClz.getGenClz().getName()))
        #创建基础类型变量的get,set方法
        for attr in clz.getAttrs():
            if attr.getIsArr():
                typeStr = Attr.convertType(attr.getType(),attr.getName())
                javaContent += JavaTemplate.PUBLIC_GENERICITY_MEMBER_GET_METHOD.format(blank = clz.getBlankStr(),genType = Attr.convertType(attr.getGenType(),None),attrType = typeStr,firstUpCaseName= CreateUtils.formatNameMethod(attr.getName()) ,attrName = attr.getName())
                javaContent += JavaTemplate.PUBLIC_GENERICITY_MEMBER_SET_METHOD.format(blank = clz.getBlankStr(),genType = Attr.convertType(attr.getGenType(),None),attrType = typeStr,firstUpCaseName= CreateUtils.formatNameMethod(attr.getName()) ,attrName = attr.getName())
            else :
                typeStr = Attr.convertType(attr.getType(),attr.getName())
                javaContent += JavaTemplate.PUBLIC_MEMBER_GET_METHOD.format(blank = clz.getBlankStr(),attrType = typeStr,firstUpCaseName= CreateUtils.formatNameMethod(attr.getName()) ,attrName = attr.getName())
                javaContent += JavaTemplate.PUBLIC_MEMBER_SET_METHOD.format(blank = clz.getBlankStr(),attrType = typeStr,firstUpCaseName= CreateUtils.formatNameMethod(attr.getName()) ,attrName = attr.getName())
        #创建对象类型变量的get,set方法
        for childClz in clz.getChildClsList():
            if isinstance(childClz,Clz):
                javaContent += JavaTemplate.PUBLIC_MEMBER_GET_METHOD.format(blank = clz.getBlankStr(),attrType = childClz.getName(),firstUpCaseName= CreateUtils.formatNameMethod(childClz.getName()) ,attrName = CreateUtils.formatNameFirstLower(childClz.getName()))
                javaContent += JavaTemplate.PUBLIC_MEMBER_SET_METHOD.format(blank = clz.getBlankStr(),attrType = childClz.getName(),firstUpCaseName= CreateUtils.formatNameMethod(childClz.getName()) ,attrName = CreateUtils.formatNameFirstLower(childClz.getName()))
            else :
                genTypeStr = CreateUtils.createGenMember('ArrayList<{0}>',childClz)
                javaContent += JavaTemplate.PUBLIC_MEMBER_GET_METHOD.format(blank = clz.getBlankStr(),attrType = genTypeStr,firstUpCaseName= CreateUtils.formatNameMethod(childClz.getGenClz().getName()) ,attrName = CreateUtils.formatNameFirstLower(childClz.getGenClz().getName()))
                javaContent += JavaTemplate.PUBLIC_MEMBER_SET_METHOD.format(blank = clz.getBlankStr(),attrType = genTypeStr,firstUpCaseName= CreateUtils.formatNameMethod(childClz.getGenClz().getName()) ,attrName = CreateUtils.formatNameFirstLower(childClz.getGenClz().getName()))
        #创建子类
        for childClz in clz.getChildClsList():
            if isinstance(childClz,Clz):
                javaContent += CreateUtils.createJavaChildClass(childClz)
            else :
                javaContent += CreateUtils.createJavaChildClass(childClz.getGenClz())
        #创建整个类
        javaWrapper = JavaTemplate.CHILD_CLASS_TEMPELATE.format(blank = clz.getBlankStr(),clzName = clz.getName() ,content = javaContent)
        return javaWrapper
    #根据python的Clz类型对象创建Java类
    @staticmethod
    def createJavaClass(clz):
        javaContent = ''
        #创建常量
        for attr in clz.getAttrs():
            if attr.getValue() is not None:
                finalItems = attr.getValue().split(',')
                for item in finalItems:
                    javaContent += JavaTemplate.PUBLIC_STATIC_FINAL_STRING_MEMBER.format(blank = clz.getBlankStr(),attrName = attr.getName().upper() +"_" + item.replace(r'::','_').upper(),attrValue = item)
        #创建基础类型变量
        for attr in clz.getAttrs():
            if attr.getIsArr():
                javaContent += JavaTemplate.PRIVATE_GENERICITY_MEMBER.format(blank = clz.getBlankStr(),genType = Attr.convertType(attr.getGenType(),None),attrType = Attr.convertType(attr.getType(),attr.getName()),attrName = attr.getName())
            else :
                if attr.getName() == 'method':
                    javaContent += JavaTemplate.PRIVATE_METHOD_MEMBER.format(blank = clz.getBlankStr(),attrType = Attr.convertType(attr.getType(),attr.getName()),attrName = attr.getName(),attrValue = attr.getValue())
                else :
                    javaContent += JavaTemplate.PRIVATE_MEMBER.format(blank = clz.getBlankStr(),attrType = Attr.convertType(attr.getType(),attr.getName()),attrName = attr.getName())
        #创建对象类型变量
        for childClz in clz.getChildClsList():
            if isinstance(childClz,Clz):
                javaContent+=JavaTemplate.PRIVATE_MEMBER.format(blank = clz.getBlankStr(),attrType = childClz.getName(),attrName = CreateUtils.formatNameFirstLower(childClz.getName()))
            else :
                genTypeStr = CreateUtils.createGenMember('ArrayList<{0}>',childClz)
                javaContent += JavaTemplate.PRIVATE_MEMBER.format(blank = clz.getBlankStr(),attrType = genTypeStr,attrName = CreateUtils.formatNameFirstLower(childClz.getGenClz().getName()))
        #创建基础类型变量的get,set方法
        for attr in clz.getAttrs():
            typeStr = Attr.convertType(attr.getType(),attr.getName())
            javaContent += JavaTemplate.PUBLIC_MEMBER_GET_METHOD.format(blank = clz.getBlankStr(),attrType = typeStr,firstUpCaseName= CreateUtils.formatNameMethod(attr.getName()) ,attrName = CreateUtils.formatNameFirstLower(attr.getName()))
            if attr.getName() != 'method':
                javaContent += JavaTemplate.PUBLIC_MEMBER_SET_METHOD.format(blank = clz.getBlankStr(),attrType = typeStr,firstUpCaseName= CreateUtils.formatNameMethod(attr.getName()) ,attrName = CreateUtils.formatNameFirstLower(attr.getName()))
        #创建对象类型变量的get,set方法
        for childClz in clz.getChildClsList():
            if isinstance(childClz,Clz):
                javaContent += JavaTemplate.PUBLIC_MEMBER_GET_METHOD.format(blank = clz.getBlankStr(),attrType = childClz.getName(),firstUpCaseName= CreateUtils.formatNameMethod(childClz.getName()) ,attrName = CreateUtils.formatNameFirstLower(childClz.getName()))
                javaContent += JavaTemplate.PUBLIC_MEMBER_SET_METHOD.format(blank = clz.getBlankStr(),attrType = childClz.getName(),firstUpCaseName= CreateUtils.formatNameMethod(childClz.getName()) ,attrName = CreateUtils.formatNameFirstLower(childClz.getName()))
            else :
                genTypeStr = CreateUtils.createGenMember('ArrayList<{0}>',childClz)
                javaContent += JavaTemplate.PUBLIC_MEMBER_GET_METHOD.format(blank = clz.getBlankStr(),attrType = genTypeStr,firstUpCaseName= CreateUtils.formatNameMethod(childClz.getGenClz().getName()) ,attrName = CreateUtils.formatNameFirstLower(childClz.getGenClz().getName()))
                javaContent += JavaTemplate.PUBLIC_MEMBER_SET_METHOD.format(blank = clz.getBlankStr(),attrType = genTypeStr,firstUpCaseName= CreateUtils.formatNameMethod(childClz.getGenClz().getName()) ,attrName = CreateUtils.formatNameFirstLower(childClz.getGenClz().getName()))
        #创建子类
        for childClz in clz.getChildClsList():
            if isinstance(childClz,Clz):
                javaContent += CreateUtils.createJavaChildClass(childClz)
            else :
                javaContent += CreateUtils.createJavaChildClass(childClz.getGenClz())
        #头部import部分
        topContent = ''
        if clz.getIsContainDate() :
            topContent += 'import ' + ExternalType.DATETIME_PACKAGE_PATH + '\n';
        if clz.getIsContainList() :
            topContent += 'import java.util.ArrayList;\n';
        #创建整个类
        javaWrapper = JavaTemplate.CLASS_TEMPELATE.format(package = clz.getPackage(),clzName = clz.getName() ,content = javaContent,topModule = topContent)
        return javaWrapper;
    #根据python的Clz类型对象创建Java文件
    @staticmethod
    def createJavaFileByClass(clz,faterPath):
        javaFile = open(faterPath + '/' + clz.name +'.java', 'w')
        javaWrapper = CreateUtils.createJavaClass(clz)
        print 'create file :' + os.path.abspath(javaFile.name) + '  Success'
        javaFile.write(javaWrapper)
    #通过jsonDoc文件生成json格式的配置
    @staticmethod
    def getConfigure():
        configureFile = open(JSON_DOC_PATH)
        try:
             configureStr = configureFile.read( )
        finally:
             configureFile.close( )
        CreateUtils.configureJson = json.loads(configureStr)
    #创建method的所有Java类文件
    @staticmethod 
    def createClassOfMethod():
        methodList = CreateUtils.configureJson['method']
        for method in methodList: 
            #请求的类型
            inputClz= CreateUtils.createClass(Clz(),method['input'])
            inputClz.setPackage('com.yealink.uc.logic.request')
            inputClz.setName(CreateUtils.formatNameMethod(method['name'] + 'Request'))
            CreateUtils.createJavaFileByClass(inputClz,REQUEST_DIR_PATH)
            #返回结果类型
            if method['ouput'] is None or method['ouput'] == 'null' : continue
            ouput= CreateUtils.createClass(Clz(),method['ouput'])
            ouput.setPackage(RESPONESE_PACKAGE_NAME)
            ouput.setName(CreateUtils.formatNameMethod(method['name'] + 'Response'))
            CreateUtils.createJavaFileByClass(ouput,RESPONESE_DIR_PATH)
    #创建notify的数据类文件
    @staticmethod 
    def createClassOfNotify():
        notifyList = CreateUtils.configureJson['notify']
        for notify in notifyList: 
            notifyClz= CreateUtils.createClass(Clz(),notify['data'])
            notifyClz.setPackage(DATA_PACKAGE_NAME)
            notifyClz.setName(CreateUtils.formatNameNotify(notify['name'] +'_Data'))
            CreateUtils.createJavaFileByClass(notifyClz,DATA_DIR_PATH)


if __name__ == "__main__":
    print "清空data目录"
    if os.path.exists(DATA_DIR_PATH):
        shutil.rmtree(DATA_DIR_PATH)
    os.mkdir(DATA_DIR_PATH)
    print "清空request目录"
    if os.path.exists(REQUEST_DIR_PATH):
        shutil.rmtree(REQUEST_DIR_PATH)
    os.mkdir(REQUEST_DIR_PATH)
    print "清空response目录"
    if os.path.exists(RESPONESE_DIR_PATH):
        shutil.rmtree(RESPONESE_DIR_PATH)
    os.mkdir(RESPONESE_DIR_PATH)

    CreateUtils.getConfigure()
    CreateUtils.createClassOfMethod()
    CreateUtils.createClassOfNotify()