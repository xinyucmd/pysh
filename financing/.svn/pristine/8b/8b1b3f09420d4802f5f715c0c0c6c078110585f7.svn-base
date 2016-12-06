package com.jiangchuanbanking.dict.service;

import java.util.List;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.dict.domain.OptionBase;

/**
 * Option service Interface
 */
public interface IOptionService<T extends OptionBase> extends IBaseService<T> {

	/**
	 * Gets option list
	 * 
	 * @param clazz
	 * @param local
	 * @return option list
	 * @throws Exception
	 */
	public List<T> getOptions(String clazz, String local) throws Exception;

	/**
	 * Gets option by ID and set label according local
	 * 
	 * @param entityClass
	 * @param id
	 * @param local
	 * @return option
	 */
	public T getOptionById(Class<T> entityClass, Integer id, String local);

	/**
	 * Gets option by ID
	 * 
	 * @param entityClass
	 * @param id
	 * @return option
	 */
	public T getOptionById(Class<T> entityClass, Integer id);

	/**
	 * Finds option by value
	 * 
	 * @param clazz
	 * @param value
	 * @return option
	 */
	public T findByValue(String clazz, String value);
	


}
