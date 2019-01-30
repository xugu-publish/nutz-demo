package com.xugu.nutz;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.NutTxDao;
import org.nutz.dao.impl.sql.NutSql;
import org.nutz.dao.sql.Sql;

public class NutzTest {
	
	private NutTxDao getDao()
	{
		Dao dao = new NutDao(C3p0Util.getDataSource());
		dao.create(TestTable.class, false);
		NutTxDao txDao = new NutTxDao(dao);
		return txDao;
	}
	
	
	//@Test
	public void nutzInsertTest()
	{
		NutTxDao txDao = getDao();
		txDao.begin(Connection.TRANSACTION_READ_COMMITTED);
		//txDao.setSavepoint("sv4");
		TestTable test = new TestTable();
		test.setName("name3");
		test.setCode("code3");
		txDao.insert(test);
		//txDao.rollback("sv4");
		txDao.commit();
	}
	//@Test
	public void updateTest()
	{
		NutTxDao txDao = getDao();
		txDao.begin(Connection.TRANSACTION_READ_COMMITTED);
		TestTable test = txDao.fetch(TestTable.class, 1);
		test.setName("name2");
		test.setCode("code2");
		txDao.update(test);
		txDao.commit();
	}
	
	//@Test
	public void deleteTest()
	{
		NutTxDao txDao = getDao();
		txDao.begin(Connection.TRANSACTION_READ_COMMITTED);
		
		TestTable test = new TestTable();
		test.setId(2);
		test.setName("name1");
		test.setCode("code1");
		txDao.deleteWith(test, "id");
		txDao.commit();
	}
	
	//@Test
	public void executeSqlTest()
	{
		NutTxDao txDao = getDao();
		txDao.begin(Connection.TRANSACTION_READ_COMMITTED);
		Sql sql = new NutSql("truncate table t_test");
		txDao.execute(sql);
		txDao.commit();
	}
	
	//@Test
	public void batchInsert()
	{
		NutTxDao txDao = getDao();
		txDao.begin(Connection.TRANSACTION_READ_COMMITTED);
		List<TestTable> batchList = new ArrayList<TestTable>();
		for(int i=100;i<500;i++)
		{
			TestTable table = new TestTable(i);
			batchList.add(table);
		}
		txDao.fastInsert(batchList);
		txDao.commit();
		batchList.clear();
	}
	
	//@Test
	public void batchUpdate()
	{
		NutTxDao txDao = getDao();
		txDao.begin(Connection.TRANSACTION_READ_COMMITTED);
		txDao.update(TestTable.class, Chain.make("code", "mycode"),Cnd.where("id", "in", new Integer[] { 100, 200 }));
		txDao.commit();
	}
	
	//@Test
	public void batchDelete()
	{
		NutTxDao txDao = getDao();
		txDao.begin(Connection.TRANSACTION_READ_COMMITTED);
		int deleteCount=txDao.clear(TestTable.class, Cnd.where("id", "between", new Integer[] { 201, 300 }));
		txDao.commit();
		Assert.assertEquals(100, deleteCount);
	}
	
	//@Test
	public void selectTest1()
	{
		NutTxDao txDao = getDao();
		txDao.begin(Connection.TRANSACTION_READ_COMMITTED);
		TestTable testTable = txDao.fetch(TestTable.class,100);
		txDao.commit();
		System.out.println(testTable.getId()+"  "+testTable.getName()+"  "+testTable.getCode());
	}
	
	@Test
	public void selectAllTest()
	{
		NutTxDao txDao = getDao();
		txDao.begin(Connection.TRANSACTION_READ_COMMITTED);
		List<TestTable> listAll = txDao.query(TestTable.class, null);
		for(int i=0;i<listAll.size();i++)
		{
			TestTable testTable = listAll.get(i);
			System.out.println(testTable.getId()+"  "+testTable.getName()+"  "+testTable.getCode());
		}
	}

}
