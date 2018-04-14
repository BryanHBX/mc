/*
 * Copyright (c) 2010-2012 Zhao.Xiang<z405656232x@163.com> Holding Limited.
 * All rights reserved.
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.edu.timelycourse.mc.biz.paging;

import com.github.pagehelper.Page;
import lombok.Data;
import org.edu.timelycourse.mc.common.constants.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Bean class for pagination.
 * It aims to receive the parameters from HttpRequest and wrappers as bean object.
 * 
 * @author Marco
 *
 */
@Data
public class PagingBean<T> implements Serializable
{
	/**
	 * The page size
	 */
	private Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
	
	/**
	 * The total entity item number
	 */
	private Long totalItems = 0L;
	
	/**
	 * The current page number
	 */
	private Integer pageNumber = 1;

	/**
	 * The total page number
	 */
	private Integer totalPageNumber = 0;

	/**
	 * The list of data items
	 */
	private List<T> items;
	
	/**
	 * The default constructor
	 */
	public PagingBean()
	{
	}
	
	/**
	 * The sole constructor
	 * 
	 * @param pageNumber
	 *          the current page
	 * @param pageSize
	 *          the page size
	 */
	public PagingBean(int pageNumber, int pageSize)
	{
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public PagingBean(final Page<T> pageEntity)
	{
		this.pageNumber = pageEntity.getPageNum();
		this.pageSize = pageEntity.getPageSize();
		this.items = pageEntity.getResult();
		this.totalItems = pageEntity.getTotal();
        this.totalPageNumber = pageEntity.getPages();
	}

}
