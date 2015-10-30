package com.culabs.nfvo.model;

import com.culabs.nfvo.util.NFVOUtils;

public class Result
{
	public static final String SUCCESS_CODE = "0";
	public static final String ERROR_CODE = "-1";
	public static final Result SUCCESS;
	public static final Result ERROR;

	private String code = "";
	private String message = "";
	private Object data;

	static
	{
		SUCCESS = new Result();
		SUCCESS.setCode("0");
		SUCCESS.setMessage("Success");
		ERROR = new Result();
		ERROR.setCode("-1");
		ERROR.setMessage("error");
	}

	public Result()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public Result(String code, String message)
	{
		super();
		this.code = code;
		this.message = message;
	}

	public Result(Integer code, String message)
	{
		super();
		this.code = String.valueOf(code);
		this.message = message;
	}

	public static class Factory
	{
		public static Result successResult()
		{
			Result res = new Result();
			res.setCode("0");
			res.setMessage("Success");
			return res;
		}

		public static Result failResult(String error, String... msgs)
		{
			Result res = new Result();
			res.setCode(NFVOUtils.isEmpty(error) ? "-1" : error);
			res.setMessage(null != msgs ? msgs[0] : "");
			return res;
		}
	}

	public String getCode()
	{
		return NFVOUtils.isEmpty(code) ? "" : code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getMessage()
	{
		return NFVOUtils.isEmpty(message) ? "" : message;
	}

	public void setMessage(String msessage)
	{
		this.message = msessage;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public boolean isSuccess()
	{
		return "0".equals(this.getCode());
	}


	public boolean isSameCode(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result other = (Result) obj;
		if (code == null)
		{
			if (other.code != null)
			{
				return false;
			}
		} else if (!code.equals(other.code))
		{
			return false;
		}
		return true;
	}

	
	@Override
	public String toString()
	{
		return "Result [code=" + code + ", message=" + message + ", data=" + data + "]";
	}

}
