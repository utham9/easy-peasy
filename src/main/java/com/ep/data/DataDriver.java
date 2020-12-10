package com.ep.data;

import java.util.List;

public interface DataDriver {

    public <T> List<T> readSource(String path);

    public <T> void writeSource(String path,T t );

}
