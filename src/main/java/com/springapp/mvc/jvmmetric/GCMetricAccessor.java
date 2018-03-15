package com.springapp.mvc.jvmmetric;

import java.util.List;

/**
 * @author songkejun
 * @create 2018-01-04 17:40
 **/
public interface GCMetricAccessor {
    List<GC> getGCList();
}
