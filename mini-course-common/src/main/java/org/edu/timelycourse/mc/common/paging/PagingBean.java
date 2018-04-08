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

package org.edu.timelycourse.mc.common.paging;

import lombok.Data;

/**
 * Bean class for pagination.
 * It aims to receive the parameters from HttpRequest and wrappers as bean object.
 * 
 * @author Marco
 *
 */
@Data
public class PagingBean
{
	/**
	 * The default page size
	 */
	public static Integer DEFAULT_PAGE_SIZE = Integer.valueOf(20);
	
	/**
	 * The default shown page count 
	 */
	public static final int DEFAULT_SHOW_PAGES = 10;
	
	/**
	 * The default target type in DWZ.
	 */
	public static final String DEFAULT_TARGET_TYPE = "navTab";
	
	/**
	 * The start index offset, by default as 0.
	 */
	private Integer startIndex = 0;
	
	/**
	 * The page size
	 */
	private Integer pageSize = DEFAULT_PAGE_SIZE;
	
	/**
	 * The total entity item number
	 */
	private Integer totalItems = 0;
	
	/**
	 * The current page number
	 */
	private Integer currentPage = 1;
	
	/**
	 * The shown page number
	 */
	private Integer pageNumShown = DEFAULT_SHOW_PAGES;
	
	/**
	 * The target type in DWZ
	 */
	private String targetType = DEFAULT_TARGET_TYPE;
	
	/**
	 * The total pages
	 */
	private Integer totalPages = 0;
	
	/**
	 * The default constructor
	 */
	public PagingBean()
	{
	}
	
	/**
	 * The sole constructor
	 * 
	 * @param currentPage
	 *          the current page
	 * @param pageSize
	 *          the page size
	 */
	public PagingBean(int currentPage, int pageSize)
	{
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.startIndex = (currentPage - 1 < 0 ? 0 : currentPage - 1) * pageSize;
	}
}
