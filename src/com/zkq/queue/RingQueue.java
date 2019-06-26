package com.zkq.queue;

import javax.sound.midi.Soundbank;

/**
 * @Author zhangkaiqiang
 * @Date 2019/6/18  10:44
 * @Description 循环队列(环形队列)
 *
 *
 * 为了解决顺序队列的假溢出问题，出现了循环队列
 * 1.front即头指针指向队列中的第一个元素
 * 2.rear即尾指针指向队列中的尾元素的下一个元素,始终留有一个空间(会造成一个空间的浪费)用于判断队列是否已满
 * 在循环队列中rear指针的移动满足下面的情况，可以简单的使用(rear+1)%maxSize==front来判满
 * if(rear+1==maxSize){
 *     rear=0;
 * }else{
 *     rear++;
 * }
 */
public class RingQueue {
	public static void main(String[] args) {
		//省略测试步骤，测试与顺序队列基本相同
	}
}

class RingQueueDemo{

	/**
	 * 队列的最大容量
	 */
	private int maxSize;

	/**
	 * 队列头指针,指向队列的第一个元素也就是array[0]
	 */
	private int front;

	/**
	 * 队列尾指针,指向队列尾元素的下一个元素
	 */
	private int rear;

	/**
	 * 保存数据的数组
	 */

	private int [] array;

	public RingQueueDemo(int maxSize){
		this.maxSize=maxSize;
		array=new int[maxSize];
	}

	/**
	 * 判断队列是否已满
	 */

	public boolean isFull(){
		//(rear+1)%maxSize是在增加一个元素后，rear的下一个位置，当该值等于front时就满了
		return (rear+1)%maxSize==front;
	}

	/**
	 * 判断队列是否为空
	 */

	public boolean isEmpty(){
		return rear==front;
	}

	/**
	 * 查看队首元素
	 */

	public  int showHead(){
		if(isEmpty()){
			System.out.println("队列为空");
			throw new RuntimeException("队列为空，无法查看");
		}
		return array[front];
	}

	/**
	 * 查看队尾元素
	 * @return
	 */
	public int showTail(){
		if(isEmpty()){
			System.out.println("队列为空，无法查看");
			throw new RuntimeException("队列为空，无法查看");
		}
		return array[rear-1];
	}

	/**
	 * 添加元素
	 */
	public void add(int o){
		if(isFull()){
			System.out.println("队列已满，无法继续添加元素");
		}else{
			//rear本身已经指向尾部元素的下一个位置即要添加的位置
			array[rear]=o;
			//把rear向后移动，当到达队列尾时通过取模再次向对头添加
			rear=(rear+1)%maxSize;
		}
	}

	/**
	 * 得到元素(先进先出)
	 */

	public int get() {
		if (isEmpty()) {
			System.out.println("队列为空");
			throw new RuntimeException("队列为空，无法得到元素");
		}

		int result=array[front];
		front=(front+1)%maxSize;
		return result;
	}


	/**
	 * 查看队列中所有元素
	 */
	public void showAll(){
		if(isEmpty()){
			System.out.println("队列为空，无法查看所有元素");
		}else{
			for (int i = front; i <front+size() ; i++) {
				System.out.println(array[i%maxSize]);
			}
		}
	}

	/**
	 * 求出环形队列中有效元素个数
	 */

	public int size(){
		//非环形时,这里rear-front既是元素个数，但是是环形时rear-front可能会出现负数,
		// 当rear-front为负数时,rear-front+maxzize的值与(rear + maxSize - front) % maxSize的值相等
		//当 当rear-front为正数时,(rear + maxSize - front) % maxSize的值刚好为有效元素个数
		return (rear + maxSize - front) % maxSize;
	}
}
