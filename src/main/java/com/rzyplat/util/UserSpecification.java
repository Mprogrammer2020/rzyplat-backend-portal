package com.rzyplat.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;

import com.rzyplat.param.UserSearchParam;

public interface UserSpecification {
    public default Query specification(UserSearchParam search) {
        Query query = new Query();
        
        String property = search.getSortBy();
        String order = search.getOrderBy();
        
        if(property != null && order != null) {
        	 if (order.equalsIgnoreCase("ASC")) {
                 query.with(Sort.by(Sort.Direction.ASC, property));
             } else if (order.equalsIgnoreCase("DESC")) {
                 query.with(Sort.by(Sort.Direction.DESC, property));
             }
        }
        return query;
    }
}
