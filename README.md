# RestoreTheWorld.java
## Animal类 动物类
  * 成员变量 id: 动物的id  
  * 成员变量 x：动物的x坐标
  * 成员变量 y: 动物的y坐标* 
  
## RestoreTheWorld类 
  * 成员方法 public String getSnapShot(String historyData,String id)
    * 获取动物在每一时刻的坐标快照
    * @param historyData 输入的每个时刻的数据
    * @param id 需要求的时刻的id
    * @return 在id时刻的所有动物的位置
  * 单元测试函数 public void testGetSnapShot()
    * 正常数据 assertEquals("cat1 12 8\ncat2 2 3", new RestoreTheWorld().getSnapShot(historyData,id));
		* 格式错误数据 assertEquals("Invalid Format", new RestoreTheWorld().getSnapShot(historyData,null));
		* 格式错误数据 assertEquals("Invalid Format", new RestoreTheWorld().getSnapShot(null,id));
		* 格式错误数据 assertEquals("Invalid Format", new RestoreTheWorld().getSnapShot(historyDataInvalidFormat,id));
		* 格式冲突数据 assertEquals("Conflict found at dcfa0c7a-5855-4ed2-bc8c-4accae8bd155", new RestoreTheWorld().getSnapShot(historyDataConflict,id));
		* 没有匹配id assertEquals("There is No match id", new RestoreTheWorld().getSnapShot(historyData,idError));
		
##运行环境：
  * jdk 1.7以上
  * junit-4.9b1.jar依赖包 

##运行方法 ： 
  * 配好环境 打开工程 选中RestoreTheWorld.java	文件，右键Run As 然后选择Junit Test。

##运行结果：
  * 单元测试没出错，控制台输出“testGetSnapShot()”
