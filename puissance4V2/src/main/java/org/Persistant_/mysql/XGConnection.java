/**
 * 
 */
package org.Persistant_.mysql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author xavier
 *
 */
public class XGConnection implements Connection {
	
	protected Connection cn;
	protected String url;
	protected String login;
	protected String passwd;

	/**
	 * 
	 */

	public XGConnection(String url, String login, String passwd) throws SQLException {
		this.url=url;
		this.login=login;
		this.passwd=passwd;
		cn=DriverManager.getConnection(url, login, passwd);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return cn.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return cn.isWrapperFor(iface);
	}

	@Override
	public Statement createStatement() throws SQLException {
		// TODO Auto-generated method stub
		return cn.createStatement();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		if (cn.isClosed())
		{
			System.out.println(cn.getWarnings());
			cn=DriverManager.getConnection(url, login, passwd);
			System.out.println("reconnexion     ????");
		}
		return cn.prepareStatement(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return cn.prepareCall(sql);
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		// TODO Auto-generated method stub
		return nativeSQL(sql);
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		// TODO Auto-generated method stub
		cn.setAutoCommit(autoCommit);
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getAutoCommit();
	}

	@Override
	public void commit() throws SQLException {
		// TODO Auto-generated method stub
		cn.commit();
	}

	@Override
	public void rollback() throws SQLException {
		// TODO Auto-generated method stub
		cn.rollback();
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		cn.close();
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return cn.isClosed();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getMetaData();
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		// TODO Auto-generated method stub
		cn.setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		// TODO Auto-generated method stub
		return cn.isReadOnly();
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		// TODO Auto-generated method stub
		cn.setCatalog(catalog);
	}

	@Override
	public String getCatalog() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getCatalog();
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		// TODO Auto-generated method stub
		cn.setTransactionIsolation(level);
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getTransactionIsolation();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getWarnings();
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		cn.clearWarnings();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return cn.createStatement(resultSetType, resultSetConcurrency);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		// TODO Auto-generated method stub
		return cn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return cn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getTypeMap();
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		cn.setTypeMap(map);
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		// TODO Auto-generated method stub
		cn.setHoldability(holdability);
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getHoldability();
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		// TODO Auto-generated method stub
		return cn.setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		// TODO Auto-generated method stub
		return cn.setSavepoint(name);
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub
		cn.rollback(savepoint);
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub
		cn.releaseSavepoint(savepoint);
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		return cn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		// TODO Auto-generated method stub
		return cn.prepareStatement(sql, resultSetType, resultSetConcurrency,resultSetHoldability);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		// TODO Auto-generated method stub
		return cn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return cn.prepareStatement(sql, autoGeneratedKeys);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return cn.prepareStatement(sql, columnIndexes);
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return cn.prepareStatement(sql, columnNames);
	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return cn.createClob();
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return cn.createBlob();
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return cn.createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return cn.createSQLXML();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return cn.isValid(timeout);
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		cn.setClientInfo(name, value);
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		// TODO Auto-generated method stub
		cn.setClientInfo(properties);
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return cn.getClientInfo(name);
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getClientInfo();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		// TODO Auto-generated method stub
		return cn.createArrayOf(typeName, elements);
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		// TODO Auto-generated method stub
		return cn.createStruct(typeName, attributes);
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub
		cn.setSchema(schema);
	}

	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getSchema();
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub
		cn.abort(executor);
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		// TODO Auto-generated method stub
		cn.setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return cn.getNetworkTimeout();
	}

}
