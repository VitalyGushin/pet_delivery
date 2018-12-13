package app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void addOrder(Date deliver, String customer, String address, String product, String phone){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("INSERT INTO orders SET added=now(), deliver=?, customer='"+customer+"'"
				+ ", address='"+address+"', product='"+product+"',phone='"+phone+"'",deliver);
	}
	
	public void changeOrder(Long id, Date newDeliver){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("UPDATE orders SET status=0, deliver=? WHERE id="+id,newDeliver);
	}
	
	public void delivereOrder(Long id){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("UPDATE orders SET status=2 WHERE id="+id);
	}
	
	public void postponeOrder(Long id){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("UPDATE orders SET status=1 WHERE id="+id);
	}
	
	public List<String> undeliveredOrders(){
		String sql = "SELECT * FROM orders WHERE status=0 ORDER BY deliver";
		List<String> ordersList = new ArrayList<String>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor<List>() {
			public List extractData(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String customer = rs.getString("customer");
					ordersList.add(customer);
				}
				return ordersList;
			}
		}	
		);
		return ordersList;
	}
	
	public List<String> postponedOrders(){
		String sql = "SELECT * FROM orders WHERE status=1 ORDER BY deliver";
		List<String> ordersList = new ArrayList<String>();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.query(sql, new ResultSetExtractor<List>() {
			public List extractData(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String customer = rs.getString("customer");
					ordersList.add(customer);
				}
				return ordersList;
			}
		}	
		);
		return ordersList;
	}
	
}
