package com.culabs.nfvo.model;

public class JSONResponse<T> {
	private Result result;
	private T data;

	public JSONResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Result getResult() {
		return null == result ? Result.Factory.failResult("-1",
				"Result is null") : result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JSONResponse [result=" + result + ", data=" + data + "]";
	}
}
