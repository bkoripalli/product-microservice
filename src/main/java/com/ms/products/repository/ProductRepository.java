package com.ms.products.repository;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ms.products.domain.Product;
import com.ms.products.domain.Spec;

@Repository
public class ProductRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Product save(Product product)  {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"insert into products (name, price, category, specs) values(?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, product.getName());
				ps.setDouble(2, product.getPrice());
				ps.setString(3, product.getCategory());
				ps.setString(4, toXml(product.getSpecs()));
				return ps;
			}
		}, holder);

		int newUserId = holder.getKey().intValue();
		product.setId(newUserId);
		return product;
	}

	public int deleteById(Long productId) {
		return jdbcTemplate.update("delete products where id = ?", productId);
	}

	public List<Product> findAll() {
		return jdbcTemplate.query("select * from products", (rs, rowNum) -> {
			return new Product(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("category"),
					fromXml(rs.getString("specs")));

		});
	}

	public Spec fromXml(String xml) {
		Spec spec = null;
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Spec.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			spec = (Spec) jaxbUnmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return spec;
	}

	public String toXml(Spec spec) {
		StringWriter sw = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Spec.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.marshal(spec, sw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sw.toString();
	}
}
