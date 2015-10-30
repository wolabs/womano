package com.culabs.nfvo.model;

import java.util.ArrayList;
import java.util.List;

public class UITableResp<T>
{
    // {"total":1,"rows":[{"id":1,"searchUserName":"名称"
    // ,"address":"地址","descriptionription":"描述"}]}
    private Integer total;
    private List<T> rows = new ArrayList<T>();
    public Integer getTotal()
    {
        return total;
    }
    public void setTotal(Integer total)
    {
        this.total = total;
    }
    public List<T> getRows()
    {
        return rows;
    }
    public void setRows(List<T> rows)
    {
        this.rows = rows;
    }
    
}
