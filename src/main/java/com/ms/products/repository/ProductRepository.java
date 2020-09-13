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

	public Product save(Product product) {
		System.out.println("Saving to db"+ Thread.currentThread().getName());
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"insert into products (name, code, release_date,description, image_name, price, category, specs) values(?,?,?,?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, product.getName());
				ps.setString(2, product.getCode());
				ps.setDate(3, product.getReleaseDate());
				ps.setString(4, product.getDescription());
				ps.setString(5, product.getImageName());
				ps.setDouble(6, product.getPrice());
				ps.setString(7, product.getCategory());
				ps.setString(8, toXml(product.getSpecs()));
				return ps;
			}
		}, holder);

		int newUserId = holder.getKey().intValue();
		product.setId(newUserId);
		return product;
	}
	
	public void update(Product product) {
		Product existingProduct = getProdcut(product.getId());
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"update products set name = ?, code = ?, release_date = ?, description = ?,image_name = ?, price = ?, category = ?, specs = ?, rating = ? where id = ?");
				ps.setString(1, getValueOrDefault(product.getName(), existingProduct.getName()));
				ps.setString(2, getValueOrDefault(product.getCode(), existingProduct.getCode()));
				ps.setDate(3, getValueOrDefault(product.getReleaseDate(), existingProduct.getReleaseDate()));
				ps.setString(4, getValueOrDefault(product.getDescription(), existingProduct.getDescription()));
				ps.setString(5, getValueOrDefault(product.getImageName(), existingProduct.getImageName()));
				ps.setDouble(6, existingProduct.getPrice());
				ps.setString(7, getValueOrDefault(product.getCategory(), existingProduct.getCategory()));
				ps.setString(8, getValueOrDefault(toXml(product.getSpecs()), toXml(existingProduct.getSpecs())));
				ps.setDouble(9, getValueOrDefault(product.getRating(), existingProduct.getRating()));
				ps.setLong(10, product.getId());
				return ps;
			}
		});
	}

	public Product getProdcut(long productId) {
		System.out.println(Thread.currentThread().getName());
		return jdbcTemplate.queryForObject("select * from products where id = ?", new Object[] { productId },
				(rs, rowNum) -> {
					return new Product(rs.getLong("id"), rs.getString("name"), rs.getString("code"),
							rs.getDouble("price"), rs.getDate("release_date"), rs.getString("category"),
							rs.getString("description"), rs.getDouble("rating"), fromXml(rs.getString("specs")),
							rs.getString("image_name"));

				});
	}

	public int deleteById(Long productId) {
		return jdbcTemplate.update("delete products where id = ?", productId);
	}

	public List<Product> findAll() {
		return jdbcTemplate.query("select * from products", (rs, rowNum) -> {
			return new Product(rs.getLong("id"), rs.getString("name"), rs.getString("code"), rs.getDouble("price"),
					rs.getDate("release_date"), rs.getString("category"), rs.getString("description"),
					rs.getDouble("rating"), fromXml(rs.getString("specs")), rs.getString("image_name"));

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
	
	public static <T> T getValueOrDefault(T value, T defaultValue) {
	    return value == null ? defaultValue : value;
	}
	
}
