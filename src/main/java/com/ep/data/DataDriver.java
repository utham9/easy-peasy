package com.ep.data;

public interface DataDriver {

    public void readSource(String path);

    public <T> void writeSource(String path,T t );

}
