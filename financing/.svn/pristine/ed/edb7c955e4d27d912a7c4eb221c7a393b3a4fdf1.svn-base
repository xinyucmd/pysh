package com.jiangchuanbanking.util;

import java.util.List;



import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.jiangchuanbanking.plan.domain.PlanBean;

public class WordBean {
	// ���һ��word ����
	private ActiveXComponent MsWordApp = null;
	// �����д����word �ĵ�
	private Dispatch document = null;
	
	public WordBean() {  
        // Open Word if we/'ve not done it already  
        if (MsWordApp == null) {  
            MsWordApp = new ActiveXComponent("Word.Application");  
        }  
    } 

	// �����Ƿ���ǰ̨�� word ���� ��
	public void setVisible(boolean visible) {
		MsWordApp.setProperty("Visible", new Variant(visible));
	}
	
    // ����һ�����ĵ�  
    public void createNewDocument() {  
        // Find the Documents collection object maintained by Word  
        // documents��ʾword�������ĵ����ڣ���word�Ƕ��ĵ�Ӧ�ó���  
        Dispatch documents = Dispatch.get(MsWordApp, "Documents").toDispatch();  
        // Call the Add method of the Documents collection to create  
        // a new document to edit  
        document = Dispatch.call(documents, "Add").toDispatch();  
    } 

	// ��һ�����ڵ�word�ĵ�,����document ���� ������
	public void openFile(String wordFilePath) {
		// Find the Documents collection object maintained by Word
		// documents��ʾword�������ĵ����ڣ���word�Ƕ��ĵ�Ӧ�ó���
		Dispatch documents = Dispatch.get(MsWordApp, "Documents").toDispatch();
		document = Dispatch.call(documents, "Open", wordFilePath,
				new Variant(false)/* �Ƿ����ת��ConfirmConversions */,
				new Variant(false)/* �Ƿ�ֻ�� */).toDispatch();
	}
	
	 public void insertText(String textToInsert) {  
	        // Get the current selection within Word at the moment.  
	        // a new document has just been created then this will be at  
	        // the top of the new doc ���ѡ �е����ݣ������һ���´������ļ��������������ݣ�����Ӧ�����ļ���ͷ��  
	        Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch();
	        moveDown(1);
	        // ȡ��ѡ��,Ӧ�þ����ƶ���� ������ ����ӵ����ݻḲ��ѡ�е�����  
	        Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));  
	        // Put the specified text at the insertion point  
	        Dispatch.put(selection, "Text", textToInsert);  
	        // ȡ��ѡ��,Ӧ�þ����ƶ����  
	        Dispatch.call(selection, "MoveRight", new Variant(1), new Variant(1));  
	    }  

	// word ���ڶԱ����б����ʱ�� �������к��� ��column ��cell
	// �����±��1��ʼ
	public void insertTable(int row, int column, List<PlanBean> planBeans, boolean flg) {
		
		String bjhj = "0";
		String lxhj = "0";
		String hkhj = "0";
		String glfhj = "0";
		String bzjhj = "0";
		int count = 0;
//		for (PlanBean planBean : planBeans) {
//			count++;
//			bjhj = BigNumberUtil.Add(bjhj,planBean.getReturnCapital());
//			lxhj = BigNumberUtil.Add(lxhj,planBean.getReturnInterest());
//			//hkhj = bjhj+Float.parseFloat(planBean.getReturnCapital());
//			glfhj = BigNumberUtil.Add(glfhj,planBean.getAccFee());
//			bzjhj = BigNumberUtil.Add(bzjhj,planBean.getPerfAmount());
//		}
//		String sybj = bjhj;
//		for (PlanBean planBean : planBeans) {
//			sybj = BigNumberUtil.Subtract(sybj, planBean.getReturnCapital());
//			planBean.setSybj(sybj);
//		}
		hkhj=BigNumberUtil.Add(hkhj,bjhj,lxhj);
		
		Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // ����������Ҫ�Ķ���
		moveDown(17);
		Dispatch.call(selection, "TypeParagraph"); // ��һ�ж���
		// �������
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		Dispatch range = Dispatch.get(selection, "Range").toDispatch();// /��ǰ���λ�û���ѡ�е�����
		Dispatch newTable = Dispatch.call(tables, "Add", range,
				new Variant(row), new Variant(column), new Variant(1))
				.toDispatch(); // ����row,column,��������
		Dispatch cols = Dispatch.get(newTable, "Columns").toDispatch(); // �˱�������У�
		int colCount = Dispatch.get(cols, "Count")
				.changeType(Variant.VariantInt).getInt();// һ���ж�����  ʵ���������==column
		// ��ͷtitle
		putTxtToCell(newTable, 1, 1, "�ڴ�");
		putTxtToCell(newTable, 1, 2, "����");
		putTxtToCell(newTable, 1, 3, "����Ӧ��");
		putTxtToCell(newTable, 1, 4, "���ڱ���");
		putTxtToCell(newTable, 1, 5, "������Ϣ");
		putTxtToCell(newTable, 1, 6, "�������");
		putTxtToCell(newTable, 1, 7, "�������");
		putTxtToCell(newTable, 1, 8, "�˻���֤��");
		
		//�����
		for (int i = 1; i <= colCount; i++) { // ѭ��ȡ��ÿһ��
			Dispatch col = Dispatch.call(cols, "Item", new Variant(i))
					.toDispatch();
			Dispatch cells = Dispatch.get(col, "Cells").toDispatch();// ��ǰ���е�Ԫ��
			int cellCount = Dispatch.get(cells, "Count")
					.changeType(Variant.VariantInt).getInt();// ��ǰ���е�Ԫ����   ʵ������������row
			for (int j = 2; j <= cellCount-1; j++) {// ÿһ���еĵ�Ԫ����
				String txt = "";
				PlanBean plan = planBeans.get(j-2);
				switch (i) {
//				case 1:
//					txt = plan.getTermNo();
//					break;
//				case 2:
//					if (flg)
//					txt = plan.getEndDate();
//					break;
//				case 3:
//					txt = BigNumberUtil.Add(plan.getReturnCapital(), plan.getReturnInterest(), plan.getAccFee());
//					break;
//				case 4:
//					txt = plan.getReturnCapital();
//					break;
//				case 5:
//					txt = plan.getReturnInterest();
//					break;
//				case 6:
//					txt = plan.getAccFee();
//					break;
//				case 7:
//					txt = plan.getSybj();
//					break;
//				case 8:
//					txt = j == cellCount-1 ? bzjhj : "";
//					break;
//				default:
//					break;
				}
				
				putTxtToCell(newTable, j, i, txt);// �������ľ��������ͬ
			}
		}
		// �ۼ���
		putTxtToCell(newTable, row, 1, "�ۼ�");
		putTxtToCell(newTable, row, 2, "");
		putTxtToCell(newTable, row, 3, BigNumberUtil.Add(bjhj, lxhj, glfhj));
		putTxtToCell(newTable, row, 4, bjhj);
		putTxtToCell(newTable, row, 5, lxhj);
		putTxtToCell(newTable, row, 6, glfhj);
		putTxtToCell(newTable, row, 7, "");
		putTxtToCell(newTable, row, 8, bzjhj);
		//Dispatch.call(selection, "TypeParagraph"); // ��һ�ж���
		System.out.println("����ƻ����");
	}

	/** */
	/**
	 * ��ָ���ĵ�Ԫ������д���
	 * 
	 * @param tableIndex
	 * @param cellRowIdx
	 * @param cellColIdx
	 * @param txt
	 */
	public void putTxtToCell(Dispatch table, int cellRowIdx, int cellColIdx,
			String txt) {
		Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),
				new Variant(cellColIdx)).toDispatch();
		Dispatch.call(cell, "Select");
		Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // ����������Ҫ�Ķ���
		Dispatch.put(selection, "Text", txt);
	}

	/** */
	/**
	 * ��ָ���ĵ�Ԫ������д���
	 * 
	 * @param tableIndex
	 * @param cellRowIdx
	 * @param cellColIdx
	 * @param txt
	 */
	public void putTxtToCell(int tableIndex, int cellRowIdx, int cellColIdx,
			String txt) {
		// ���б��
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
		// Ҫ���ı��
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),
				new Variant(cellColIdx)).toDispatch();
		Dispatch.call(cell, "Select");
		Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // ����������Ҫ�Ķ���
		Dispatch.put(selection, "Text", txt);
	}

	public void moveUp(int pos) {
		Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // ����������Ҫ�Ķ���
		for (int i = 0; i < pos; i++) {
			Dispatch.call(selection, "MoveUp");
		}
	}
	
	public void moveDown(int pos) {
		Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // ����������Ҫ�Ķ���
		for (int i = 0; i < pos; i++) {
			Dispatch.call(selection, "MoveDown");
		}
	}

	/**
	 * ��ѡ�����ݻ����㿪ʼ�����ı�
	 * 
	 * @param toFindText
	 *            Ҫ���ҵ��ı�
	 * @return boolean true-���ҵ���ѡ�и��ı���false-δ���ҵ��ı�
	 */
	public boolean find(String toFindText) {
		if (toFindText == null || toFindText.equals(""))
			return false;
		Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // ����������Ҫ�Ķ���
		// ��selection����λ�ÿ�ʼ��ѯ
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();
		// ����Ҫ���ҵ�����
		Dispatch.put(find, "Text", toFindText);
		// ��ǰ����
		Dispatch.put(find, "Forward", "True");
		// ���ø�ʽ
		Dispatch.put(find, "Format", "True");
		// ��Сдƥ��
		Dispatch.put(find, "MatchCase", "True");
		// ȫ��ƥ��
		Dispatch.put(find, "MatchWholeWord", "True");
		// ���Ҳ�ѡ��
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/** */
	/**
	 * ��ѡ��ѡ�������趨Ϊ�滻�ı�
	 * 
	 * @param toFindText
	 *            �����ַ�
	 * @param newText
	 *            Ҫ�滻������
	 * @return
	 */
	public boolean replaceText(String toFindText, String newText) {
		if (!find(toFindText))
			return false;
		Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // ����������Ҫ�Ķ���
		Dispatch.put(selection, "Text", newText);
		return true;
	}

	public void printFile() {
		// Just print the current document to the default printer
		Dispatch.call(document, "PrintOut");
	}

	// �����ĵ��ĸ��
	public void save() {
		Dispatch.call(document, "Save");
	}

	public void saveFileAs(String filename) {
		Dispatch.call(document, "SaveAs", filename);
	}

	public void closeDocument() {
		Dispatch.call(document, "Close", new Variant(0));
		document = null;
	}

	public void closeWord() {
		Dispatch.call(MsWordApp, "Quit");
		MsWordApp = null;
		document = null;
	}

	// ����wordApp�򿪺󴰿ڵ�λ��
	public void setLocation() {
		Dispatch activeWindow = Dispatch.get(MsWordApp, "Application")
				.toDispatch();
		Dispatch.put(activeWindow, "WindowState", new Variant(1)); // 0=default
		// 1=maximize
		// 2=minimize
		Dispatch.put(activeWindow, "Top", new Variant(0));
		Dispatch.put(activeWindow, "Left", new Variant(0));
		Dispatch.put(activeWindow, "Height", new Variant(600));
		Dispatch.put(activeWindow, "width", new Variant(800));
	}
	
	
   public void test(){  
	   
//	    Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch(); // ����������Ҫ�Ķ���
//		moveDown(277);
//		
//		Dispatch tables = Dispatch.get(document, "Tables").toDispatch();
//		Dispatch range = Dispatch.get(selection, "Range").toDispatch();// /当前光标位置或者选中的区域
//		Dispatch newTable = Dispatch.call(tables, "Add", range,
//				new Variant(1), new Variant(8), new Variant(1))
//				.toDispatch(); // 设置row,column,表格外框宽度
//		Dispatch cols = Dispatch.get(newTable, "Columns").toDispatch(); // 此表的所有列，
//		int colCount = Dispatch.get(cols, "Count")
//				.changeType(Variant.VariantInt).getInt();// 一共有多少列  实际上这个数==column
//
		Dispatch tables = Dispatch.get(document, "Tables").toDispatch(); 
		
		
		Dispatch table = Dispatch.call(tables, "Item", new Variant(2)) .toDispatch(); 
			
		
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch(); 
	
		
		Dispatch.call(rows, "Add");  
		
	}
   
   
   
 


}
