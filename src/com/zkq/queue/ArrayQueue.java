package com.zkq.queue;

import java.util.Scanner;

/**
 * @Author zhangkaiqiang
 * @Date 2019/6/17  20:38
 * @Description 使用数组模拟队列
 *
 * 存在的问题:
 * 该队列不是环形队列，当头指针和尾指针都移动到队尾时，队列前面的空间不再能够使用，会造成空间的严重浪费
 */
public class ArrayQueue {

	public static void main(String[] args) {

		MyQueue my=new MyQueue(3);
		Scanner scanner=new Scanner(System.in);
		char s;
		boolean flag=true;
		while(flag){
			System.out.println("--------------------------菜单--------------------------");
			System.out.println("输入e(exit),退出程序");
			System.out.println("输入a(add),向队列中添加元素");
			System.out.println("输入g(get),从队列中得到元素");
			System.out.println("输入h(head),查看队列头部元素");
			System.out.println("输入t(tail),查看队列尾部元素");
			System.out.println("输入s(showAll),查看队列中全部元素");
			System.out.println("-------------------------------------------------------");
			System.out.print("您要输入的命令是:");
			//接收一个字符
			s=scanner.next().charAt(0);
			switch (s){
				case 'a':
					System.out.print("请输入要添加的数");
					int res=scanner.nextInt();
					my.add(res);
					break;
				case 'e':
					flag=false;
					break;
				case 'h':
					int head=my.showHead();
					System.out.println("队列头部元素为"+head);
					break;
				case 'g':
					int result=my.get();
					System.out.println("从队列中得到元素为"+result);
					break;
				case 't':
					int tail=my.showTail();
					System.out.println("队列尾部元素为"+tail);
					break;
				case 's':
					System.out.println("队列中全部元素为:");
					my.showAll();
				default:
						break;

			}
		}

	}
}

/**
 * 使用数组模拟队列
 */

class MyQueue{

	/**
	 * 数组最大容量
	 */
	private int maxSize;

	/**
	 * 用于存储数据的数组
	 */
	private int [] array;

	/**
	 * 头指针,指向头部但并不包含头部第一个元素，指向头部之前的位置
	 */
	private int front;

	/**
	 * 尾指针，指向队列尾部的最后一个数据
	 */
	private int rear;

	/**
	 * 构造方法，创建队列并且进行初始化
	 */
	public MyQueue(int maxSize){
		this.maxSize=maxSize;
		this.array=new int [maxSize];
		this.front=-1;
		this.rear=-1;

	}

	/**
	 * 向队列中添加元素
	 */
	public void add(int o){
		//判断该队列是否已满
		if(!isFull()){
			//让尾指针向后移动
			rear++;
			array[rear]=o;
		}else{
			System.out.println("队列已满，无法继续添加");
		}
	}

	/**
	 * 从队列中取出元素
	 */

	public int get(){
		int num = 0;
		//判断队列是不是空的
		if(!isEmpty()){
			//因为front指向-1位置，故先向后移动一下
			front++;
			num=array[front];
		}else{
			throw new RuntimeException("队列中没有元素了");
		}
		return num;
	}

	/**
	 * 判断队列是否满l
	 * @return
	 */
	public boolean isFull(){
		return rear==maxSize-1;
	}

	/**
	 * 判断队列是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return rear==front;
	}

	/**
	 * 查看队列中的第一个元素
	 */
	public int showHead(){
		if(isEmpty()){
			System.out.println("队列为空，无法取出数据");
		}
		return array[front+1];
	}

	/**
	 *查看队列中尾部元素
	 * @return
	 */
	public int showTail(){
		if (isEmpty()){
			System.out.println("队列为空，无法取出数据");
		}
		//指向尾部，不是尾部的下一个元素
		return array[rear];
	}

	/**
	 * 查看队列中的所有元素
	 */

	public void showAll(){
		if(isEmpty()){
			System.out.println("队列为空，不存你在元素");
		}else{
			for(int item:array){
				System.out.print(item);
				System.out.print("    ");
				System.out.println();
			}
		}

	}
}