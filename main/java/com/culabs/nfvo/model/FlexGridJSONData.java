package com.culabs.nfvo.model;

import java.util.ArrayList;
import java.util.List;

public class FlexGridJSONData {
	private int page = 1;
	private int total = 1;
	private List<RowData> rows = new ArrayList<RowData>();
	private Object rowid = null;
	private List<Object> coldatas = null;

	public static class Factory {
		public static FlexGridJSONData newInstance() {
			return new FlexGridJSONData();
		}
	}

	/**
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置页码。
	 * 
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return this.total;
	}

	/**
	 * 设置总记录数
	 * 
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	public List<RowData> getRows() {
		return rows;
	}

	public void setRows(List<RowData> rows) {
		this.rows = rows;
	}

	public void addRow(Object rowid, List<Object> coldatas) {
		RowData rd = new RowData(rowid, coldatas);
		this.rows.add(rd);
	}

	/**
	 * 设置每一行的id。 配合addColdata(),commitData()方法是用。 例：setRowId("row1");
	 * addColdata("1"); addColdata("2");
	 * 
	 * setRowId("row2"); addColdata("a"); addColdata("b");
	 * 
	 * commitData(); 表示 1，2两个数据都为行row1中第一列，第二列的数据。 a,b 两个数据都为row2中第一列，第二列的数据。
	 * commitData()的调用表示，row2行的数据已经组织完成。 在设置row2行的数据时，会自动提交row1行的数据。
	 * 
	 * @param rowid
	 */
	public void setRowId(Object rowid) {
		commitData();
		this.rowid = rowid;
		this.coldatas = null;
	}

	/**
	 * 该方法配合setRowId和commitData()使用。该方法必须在调用setRowId()后调用，
	 * 否则会抛出NullPointerException 请参考setRowId的说明
	 * 
	 * @param coldata
	 *            每一列数据
	 */
	public void addColdata(Object coldata) {
		if (rowid == null)
			throw new NullPointerException("please set rowid");
		if (coldatas == null)
			coldatas = new ArrayList<Object>();

		coldatas.add(coldata);
	}

	public void commitData() {
		if (this.rowid != null && this.coldatas != null) {
			addRow(this.rowid, this.coldatas);
			this.rowid = null;
			this.coldatas = null;
		}
	}

	/*
	 * 这里生成的是符合flexgrid for jquery 的json格式字符串 其格式如下： { page:1, total:0, rows:[
	 * {id:'row2',cell:['col','col','col','col','col','col']},
	 * {id:'row3',cell:['col','col','col','col','col','col']},
	 * {id:'row1',cell:['col','col','col','col','col','col']} ] }
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{\r\n");
		sb.append("page:").append(page).append(",\r\n");
		sb.append("total:").append(total).append(",\r\n");
		sb.append("rows:[\r\n");

		int keynum = 1;
		List<RowData> rowdatalist = this.rows;
		for (RowData rowdata : rowdatalist) {
			sb.append("  {id:'").append(rowdata.getRowid()).append("',")
					.append("cell:[");
			int i = 1;
			List<Object> coldatalist = rowdata.getCell();
			for (Object data : coldatalist) {
				sb.append("'").append(data).append("'");
				if (i < coldatalist.size()) {
					sb.append(",");
				}
				i++;
			}

			if (keynum < rowdatalist.size()) {
				sb.append("]},\r\n");
			} else {
				sb.append("]}\r\n");
			}

			keynum++;
		}
		sb.append("  ]\r\n}");
		return sb.toString();
	}

	public class RowData {
		Object rowid = null;
		List<Object> cell = null;

		public RowData() {
		}

		public RowData(Object rowid, List<Object> coldata) {
			this.rowid = rowid;
			this.cell = coldata;
		}

		public List<Object> getCell() {
			return cell;
		}

		public Object getRowid() {
			return rowid;
		}

		public void setCell(List<Object> coldata) {
			this.cell = coldata;
		}

		public void setRowid(String rowid) {
			this.rowid = rowid;
		}
	}

	public static void main(String args[]) {

		ArrayList<String> list = new ArrayList<String>();
		list.add("col");
		list.add("col");
		list.add("col");
		list.add("col");
		list.add("col");
		list.add("col");
		FlexGridJSONData fgjd = new FlexGridJSONData();
		fgjd.setRowId("row1");
		fgjd.addColdata("cols");
		fgjd.addColdata("cols");
		fgjd.addColdata("cols");
		fgjd.setRowId("row2");
		fgjd.addColdata("cols");
		fgjd.addColdata("cols");
		fgjd.addColdata("cols");
		fgjd.setRowId("row3");
		fgjd.addColdata("cols");
		fgjd.addColdata("cols");
		fgjd.addColdata("cols");
		fgjd.commitData();
	}

}
