package com.zkq.linkedlist;

import java.util.List;
import java.util.Objects;

/**
 * @Author zhangkaiqiang
 * @Date 2019/6/18  16:18
 * @Description 链表
 *
 * 1.链表是有序列表,以节点的方式来存储数据的,在内存中不需要连续的存储单元进行数据的存储
 * 2.链表中的每个节点包含data域, next域(指向下一个节点)以及prev节点(指向前一个节点)
 * 3.链表分带头节点的链表和没有头节点的链表，根据实际的需求来确定
 * 4.链表分为单链表和双向链表，单链表只存在next指针,双向链表即存在prev指针也存在next指针
 *
 * 下面代码完成无头节点的双向链表的增删改查操作,有无头结点是指是否存在一个仅仅将节点作为头结点但是并不不存储数据的节点
 */
public class LinkedListDemo<T> {


	/**
	 * 头节点
	 */
	private Node<T> first=null;

	/**
	 * 尾节点
	 */
	private Node<T> last=null;


	/**
	 * 节点删除的标志位
	 */
	private boolean delFlag=false;

	/**
	 * 某个数据在链表中的位置
	 */
	private int index=0;

	/**
	 * 定义链表中的节点
	 * @param <T>
	 */
	static class Node<T>{
		/**
		 * 节点中的数据
		 */
		private T data;
		/**
		 * 尾指针,指向下一个节点
		 */
		private Node<T> next;

		/**
		 * 头指针,指向前一个节点
		 */
		private Node<T> prev;

		Node(T data,Node<T> next,Node<T> prev){
			this.next=next;
			this.data=data;
			this.prev=prev;
		}
	}

	/**
	 * 向链表中添加数据，添加数据时创建一个新的节点连接在链表尾部,
	 * 	先判断最后一个节点是不是null,如果是null表示第一次添加元素，则进行初始化
	 *
	 * 	@param element 要增加的数据
	 */
	public void addNode(T element){
		Node<T> newNode;
		if(first==null){
			//未初始化链表，链表为null,初始化后第一个元素即为头结点也是尾结点
			newNode=new Node<T>(element,null,null);
			first=newNode;
		}else{
			//已经初始化了,进行数据添加,前驱节点为尾结点，后继节点为null
			newNode=new Node<T>(element,null,last);
			last.next=newNode;
		}
		last=newNode;
	}


	/**
	 * 在链表的特点位置插入元素
	 * @param index 元素插入位置
	 * @param element 要插入的元素
	 * @param linkedListDemo 元素要插入到的链表
	 */
	public void addIndexNode(int index,T element,LinkedListDemo linkedListDemo){
		if(index<0||index>indexOfList(last.data,linkedListDemo)){
			throw new RuntimeException("元素位置不正确,请检查元素插入的位置");
		}else{
			// 尾部插入
			if(index==indexOfList(last.data,linkedListDemo)){
				addNode(element);
			}else if (index==0){
				// 头部插入
				Node<T> node=new Node<>(element,first,null);
				first=node;
			}else{
				// 插入到中间
				Node<T> node=new Node(element,linkedListDemo.getNode(index,linkedListDemo),linkedListDemo.getNode(index-1,linkedListDemo));
				linkedListDemo.getNode(index,linkedListDemo).prev=node;
				linkedListDemo.getNode(index-1,linkedListDemo).next=node;
			}
		}
	}


	/**
	 * 删除链表中的数据
	 * @param element 数据
	 * @return 删除是否成功
	 */
	public boolean delNode(T element){
		Node<T> node=first;
		if(node==null){
			throw new RuntimeException("链表为空，未进行初始化，不能进行删除操作");
		}
		for(;node!=null;node=node.next){
			if(node.data==element){
				//如果删除的是最后一个节点时
				if(node==last){
					last=node.prev;
					node.prev.next=null;
					delFlag=true;
				}else{
					node.prev.next=node.next;
					node.next.prev=node.prev;
					delFlag=true;
				}

			}
		}
		return delFlag;
	}


	/**
	 * 根据位置得到链表中的元素
	 * @param index
	 * @param linkedListDemo
	 * @return
	 */
	private Node<T> getNode(int index,LinkedListDemo linkedListDemo){
		Node<T> node=first;
		if(index<0||index>indexOfList(last.data,linkedListDemo)){
			throw new RuntimeException("位置为非法参数，请进行检查");
		}else{
			int count=0;
			while(node!=null){
				if(index==count){
					break;
				}
				node=node.next;
				count++;
			}
		}
		return  node;
	}
	/**
	 * 得到第一个数据
	 * @param
	 */

	public T getFirst(){
		if(first==null){
			throw  new RuntimeException("链表为进行初始化，不能获取元素");
		}
		return first.data;
	}


	/**
	 * 得到最后一个元素
	 * @return
	 */
	public T getLast(){
		if(last==null){
			throw  new RuntimeException("链表为进行初始化，不能获取元素");
		}
		return last.data;
	}


	/**
	 * 链表的遍历
	 */
	public void iteratorLinkedList(){
		Node<T> temp=first;
		if(temp==null){
			throw new RuntimeException("链表为空，无法遍历");
		}
		//当退出while循环时，temp指向链表的最后
		while(temp.next!=null){
			System.out.println(temp.data);
			temp=temp.next;
		}
		System.out.println(last.data);
	}


	/**
	 * 得到某个元素在列表中的位置
	 * @param element 待查找位置的元素
	 * @return int 元素在链表中的位置
	 */
	public int indexOfList(T element,LinkedListDemo linkedListDemo){
		Objects.requireNonNull(linkedListDemo);
		// 保证头指针始终指向第一个元素
		Node<T> head=first;
		while(head.next!=null){
			if(head.data==element){
				return index;
			}else{
				head=head.next;
				index++;
			}
		}
		if(last.data==element){
			return index;
		}
		return -1;
	}



	public static void main(String[] args) {

		LinkedListDemo<Integer> linkedListDemo=new LinkedListDemo<>();
		//添加元素
		linkedListDemo.addNode(1);
		linkedListDemo.addNode(2);
		linkedListDemo.addNode(3);

		//遍历输出
		System.out.println("链表中包含以下元素");
		linkedListDemo.iteratorLinkedList();


		//删除元素后再遍历输出
		//System.out.println("删除元素结果为:"+linkedListDemo.delNode(2));
		//System.out.println("执行删除操作后,链表中的元素");
		//linkedListDemo.iteratorLinkedList();


		//System.out.println("链表中的第一个元素"+linkedListDemo.getFirst());
		//System.out.println("链表中的最后一个元素"+linkedListDemo.getLast());


		System.out.println("2在链表中的位置"+linkedListDemo.indexOfList(2,linkedListDemo));

		linkedListDemo.addIndexNode(0,5,linkedListDemo);

		System.out.println(linkedListDemo.getNode(0,linkedListDemo).data);
	}

}
