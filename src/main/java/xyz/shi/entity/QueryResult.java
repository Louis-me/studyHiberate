package xyz.shi.entity;
import java.util.List;

public class QueryResult {

    private int count; // 总记录数
    private List list; // 一页的数据

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
    public void setList(List list) {
        this.list = list;
    }

    public List getList() {
        return list;
    }

    public QueryResult(int count, List list) {
        this.count = count;
        this.list = list;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "count=" + count +
                ", list=" + list +
                '}';
    }
}

