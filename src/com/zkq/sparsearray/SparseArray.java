package com.zkq.sparsearray;


import java.io.*;

/**
 * @Author zhangkaiqiang
 * @Date 2019/6/17  9:28
 * @Description 稀疏数组用于压缩数组，减小存储空间消耗
 *
 * 模拟棋盘存盘过程
 *
 * 存盘操作步骤:原二维数组--->稀疏数组---->文件
 * 读盘操作步骤:文件----->稀疏数组---->原二维数组
 *
 *
 * 二维数组转稀疏数组的思路
	    1. 遍历原始的二维数组，得到有效数据的个数 sum
		2. 根据sum 就可以创建 稀疏数组 sparseArr   int[sum + 1] [3]
		3. 将二维数组的有效数据数据存入到 稀疏数组

	稀疏数组转原始的二维数组的思路
		1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的  chessArr2 = int [11][11]
		2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
 */
public class SparseArray {
	public static void main(String[] args) {

		//创建并填充原始的二维数组
		int[][] ints = new int[11][11];
		ints[1][2]=1;
		ints[2][3]=2;
		ints[4][5]=2;


		System.out.println("原始二维数组");
		for(int [] row:ints){
			for(int j:row){
				System.out.print(j);
				System.out.print("  ");
			}
			System.out.println();
		}

		System.out.println("转换得到稀疏数组并向文件中写入稀疏数组");
		int [] [] result=ChangeToSparseArray(ints);
		writeToText("E:\\棋盘.txt",result);



		System.out.println("从文件中读出的稀疏数组");
		int [][] sparseArray=readerFromText("E:\\棋盘.txt");
		for(int [] row:sparseArray){
			for(int item:row){
				System.out.print(item);
				System.out.print("  ");
			}
			System.out.println();
		}

		//转换并打印原二维数组
		System.out.println("把从文件中得到的稀疏数组还原为原始二维数组并进行打印");
		int [][] array=ChangeToArray(sparseArray);
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j <array[i].length ; j++) {
				System.out.print(array[i][j]);
				System.out.print("    ");
			}
			System.out.println();
		}
	}


	/**
	 * 原二维数组转换为稀疏数组
	 * @param array
	 * @return
	 */
	public static int [] [] ChangeToSparseArray(int [] [] array){
		//该值用于记录二维数组中一共有多少个有效值(非0值)
		int count=0;
		//遍历得到有效值
		for (int i = 0; i <array.length ; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if(array[i][j]!=0){
					count++;
				}
			}
		}
		//创建稀疏数组
		int [] [] sparseArray=new int [count+1] [3];
		//向稀疏数组中填充第一行数据(即原二维数组有几行几列和有效数值)
		sparseArray[0][0]=array.length;
		sparseArray[0][1]=array[0].length;
		sparseArray[0][2]=count;
		int num=1;
		//向稀疏数组中填充其余数据
		for (int i = 0; i <array.length ; i++) {
			for (int j = 0; j <array[i].length ; j++) {
				if(array[i][j]!=0){
					sparseArray[num][0]=i;
					sparseArray[num][1]=j;
					sparseArray[num][2]=array[i][j];
					num++;
				}
			}
		}
		return sparseArray;
	}


	/**
	 * 把稀疏数组恢复为原始二维数组
	 * @return
	 */
	public static int [] [] ChangeToArray(int [][] SparseArray){

		int row=SparseArray[0][0];
		int cloum=SparseArray[0][1];
		int [][] result=new int [row][cloum];
		//向原二维数组填充数据
		for (int i = 1; i <SparseArray.length; i++) {
			int m=SparseArray[i][0];
			int j=SparseArray[i][1];
			result[m][j]=SparseArray[i][2];
		}
		return result;
	}


	/**
	 * 把转换结果即稀疏数组写出到文件中
	 * @param filePath
	 * @param array
	 */
	public static void writeToText(String filePath,int [][] array){
		File file=new File(filePath);
		try (Writer out=new FileWriter(file) {
		}){
			for (int i = 0; i <array.length ; i++) {
				for (int j = 0; j <array[i].length ; j++) {
					out.write(array[i][j]+"\t");
				}
				out.write("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 从文件中读出保存的稀疏数组还原成稀疏数组
	 */

	public static int [][] readerFromText(String filePath){
		File file=new File(filePath);
		String line;
		int row=0;
		int num=0;
		int [][] array=null;
		try (Reader in=new FileReader(file);
			BufferedReader bufferedReader=new BufferedReader(in)){
			/**
			 * mark()方法的入参是什么意思?
			 */
			bufferedReader.mark( ( int )file.length() + 1 );
			//先遍历得到稀疏数组的行数，不然无法创建数组
			while(bufferedReader.readLine()!=null){
				row++;
			}
			array=new int [row][3];
			//对稀疏数组进行赋值
			bufferedReader.reset();
			while((line=bufferedReader.readLine())!=null){
				String[] temp = line.split("\t");
				array[num][0]=Integer.valueOf(temp[0]);
				array[num][1]=Integer.valueOf(temp[1]);
				array[num][2]=Integer.valueOf(temp[2]);
				num++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return array;
	}
}
