/**
 * Copyright(c)2012
 * DHC Software Co., Ltd.
 *
 * All Rights Reserved
 *
 * Revision History:
 *                       Modification        Tracking
 * Author (Email ID)     Date                Number              Description
 * xiongxiaoming         2012-4-23             BugId
 *
 */
package com.jiangchuanbanking.util;

/**
 * @author xiongxiaoming
 * 
 */
public class ExceptionHandler {
	
	public static void rethrow(Throwable t) {
		t.printStackTrace();
		if (t instanceof RuntimeException) {
			throw (RuntimeException) t;
		} else {
			throw new RuntimeException(t);
		}
	}
}
