package com.xin.myblog.pojo;

public class Page {
    //当前页码
    private int current = 1;
    //显示上限
    private int limit = 5;
    //数据总数
    private int rows;
    //查询路径(用于复用分页链接)
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
            this.limit = limit;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //获取数据的起始行
    public int getOffset() {
        return (current - 1) * limit;
    }

    //获取总页数
    public int getTotal() {
        if (rows % limit == 0) {
            return rows / limit;
        }else {
            return rows / limit + 1;
        }
    }

    //获取起始页码
    public int getBegin() {
        int begin = current - 2;
        return begin > 0 ? begin : 1;
    }

    //获取结束页码
    public int getEnd() {
        int end = current + 2;
        int total = getTotal();
        return end > total ? total : end;
    }

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", limit=" + limit +
                ", rows=" + rows +
                ", path='" + path + '\'' +
                '}';
    }
}
