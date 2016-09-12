package com.lee.wait;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Animal 动物的类
 * @author wait
 * @time 2016-9-11 15:02:29
 * @version 1.0
 */
class Animal{
	
	private String id;//动物的id 
	private int x;//动物的x坐标
	private int y;//动物的y坐标
	/**
	 * 默认构造函数
	 */
	public Animal(){
		
	}
	/**
	 * 构造函数
	 * @param id 动物的id
	 * @param x  动物所处的位置 x坐标
	 * @param y  动物所处的位置 y坐标
	 */
	public Animal(String id, int x, int y) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
	}
	/**
	 * 获取动物id
	 * @return 动物id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置动物id
	 * @param id 要设置的动物id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取动物坐标的x坐标
	 * @return 动物的x坐标
	 */
	public int getX() {
		return x;
	}
	/**
	 * 设置动物的x坐标
	 * @param x 要设置的x坐标
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * 获取动物坐标的y坐标
	 * @return 动物的y坐标
	 */
	public int getY() {
		return y;
	}
	/**
	 * 设置动物的y坐标
	 * @param y 要设置的y坐标
	 */
	public void setY(int y) {
		this.y = y;
	}
	
}
/**
 * 
 * @author wait
 *
 */
public class RestoreTheWorld {
	/**
	 * 获取动物在每一时刻的坐标快照
	 * @param historyData 输入的每个时刻的数据
	 * @param id 需要求的时刻的id
	 * @return 在id时刻的所有动物的位置
	 */
	public String getSnapShot(String historyData,String id){
		if(historyData==null||id==null){
			return "Invalid Format";
		}
		List<Animal> listAnimal=new ArrayList<>();	//存储出现过的动物
		List<String> listId=new ArrayList<>();		//存储出现过的数据的ID
		List<Date> listDatetime=new ArrayList<>();	//存储数据中出现的时间
		
		BufferedReader br=new BufferedReader(new StringReader(historyData));//读取字符串的BufferedReader
		StringBuilder resSb=new StringBuilder();
		String strId;//第一行，当前时刻的id
		try {
			while((strId=br.readLine())!=null){
				//每组数组里面，第一行为当前时刻的全局为一id，非空、不包含空格，唯一
				//为空时、包含空格时、已经存在时
				if(strId==""||strId.contains(" ")||listId.contains(strId)){
					return  "Invalid Format";
				}else{
					//第二行，当前时刻，格式为YYYY/mm/dd hh:MM:ss
					String strDatetime=br.readLine();

					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					format.setLenient(false);
					Date date = null;
					try {
						if(!listDatetime.isEmpty()){
							date=format.parse(strDatetime);
							//如果后出现的日期比前面的日期早，那么数据冲突
							if(!date.after(listDatetime.get(listDatetime.size()-1))){
								return  "Conflict found at "+strId;
							}else{
								listDatetime.add(date);
							}
						}else{
							listDatetime.add(format.parse(strDatetime));
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
						return  "Invalid Format";
					}
					String strData;//读取的动物数据
					//读取本组数据的动物数据，当数据为空行时，表明一组的数据结束。
					while((strData=br.readLine())!=null&&!strData.equals("")){
						String[] datas=strData.split("\\s+");
						if(datas.length==3){
							//当数据为项为3的时候，说明当前动物是刚出现
							for(int k=0,size=listAnimal.size();k<size;k++){
								//如果当前动物出现过，说明数据冲突
								if(listAnimal.get(k).getId()==datas[0]){
									return  "Conflict found at "+strId;
								}
							}
							listAnimal.add(new Animal(datas[0],Integer.parseInt(datas[1]),Integer.parseInt(datas[2])));
						}else if(datas.length==5){
							//当数据为项为5的时候，说明当前动物之前出现过
							int k=0,size=listAnimal.size();
							for(;k<size;k++){
								//如果当前动物出现过，说明数据格式错误
								Animal animal=listAnimal.get(k);
								if(animal.getId().equals(datas[0])){
									if(animal.getX()==Integer.parseInt(datas[1])&&animal.getY()==Integer.parseInt(datas[2])){
										animal.setX(Integer.parseInt(datas[1])+Integer.parseInt(datas[3]));
										animal.setY(Integer.parseInt(datas[2])+Integer.parseInt(datas[4]));
										break;
									}else{
										//当找到了出现过的动物，但是位置校验错误，数据冲突
										return  "Conflict found at "+strId;
									}
								}
							}
							if(k==size){
								//如果没有找到出现过的动物，那么数据冲突
								return  "Conflict found at "+strId;
							}
						}else{
							return  "Invalid Format";
						}
					}
					//这组数据读取结束,如果当前strId和需要求的id相同，那么输出当前时刻所有动物的状态
					if(strId.equals(id)){
						for(int k=0,size=listAnimal.size();k<size;k++){
							Animal animal =listAnimal.get(k);
							resSb.append(animal.getId()+" "+animal.getX()+" "+animal.getY());
							if(k<size-1){
								resSb.append("\n");
							}
						}
						//在这里不返回时为了保证验证完所有数据
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(resSb.length()>0){
			return resSb.toString();
		}else{
			//没有匹配到所有需要查询的id，返回
			return "There is No match id";
		}
	}
	@org.junit.Test
	public void testGetSnapShot(){
		//正常数据
		String historyData=
				  "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n"
				+ "2016/09/02 22:30:46\n"
				+ "cat1 10 9\n"
				+ "\n"
				+ "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n"
				+ "2016/09/02 22:30:52\n"
				+ "cat1 10 9 2 -1\n"
				+ "cat2 2 3\n"
				+ "\n"
				+ "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155\n"
				+ "2016/09/02 22:31:02\n"
				+ "cat1 12 8 3 4\n";
		//格式错误的数据
		String historyDataInvalidFormat=
				  "e4e87cb2-8e 9a-4749-abb6-26c59344dfee\n"
				+ "2016/09/02 22:30:46\n"
				+ "cat1 10 9\n"
				+ "\n"
				+ "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n"
				+ "2016/09/02 22:30:52\n"
				+ "cat1 10 9 2 -1\n"
				+ "cat2 2 3\n"
				+ "\n"
				+ "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155\n"
				+ "2016/09/02 22:31:02\n"
				+ "cat1 12 8 3 4\n";
		//数据冲突的数据
		String historyDataConflict=
				  "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n"
				+ "2016/09/02 22:30:46\n"
				+ "cat1 10 9\n"
				+ "\n"
				+ "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n"
				+ "2016/09/02 22:30:52\n"
				+ "cat1 10 9 2 -1\n"
				+ "cat2 2 3\n"
				+ "\n"
				+ "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155\n"
				+ "2016/09/02 22:31:02\n"
				+ "cat1 11 8 3 4\n";
		//能够找到的id
		String id="351055db-33e6-4f9b-bfe1-16f1ac446ac1";
		//数据中不存在的id
		String idError="351055db-33e6-4f9b-bfe1-16f1ac446ac2";
		
		assertEquals("cat1 12 8\ncat2 2 3", new RestoreTheWorld().getSnapShot(historyData,id));
		assertEquals("Invalid Format", new RestoreTheWorld().getSnapShot(historyData,null));
		assertEquals("Invalid Format", new RestoreTheWorld().getSnapShot(null,id));
		assertEquals("Conflict found at dcfa0c7a-5855-4ed2-bc8c-4accae8bd155", new RestoreTheWorld().getSnapShot(historyDataConflict,id));
		assertEquals("Invalid Format", new RestoreTheWorld().getSnapShot(historyDataInvalidFormat,id));
		assertEquals("There is No match id", new RestoreTheWorld().getSnapShot(historyData,idError));
		
		System.out.println("testGetSnapShot()");  
	}
}
