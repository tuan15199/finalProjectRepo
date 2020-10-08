package com.project.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.dtos.CatalogDto;
import com.project.dtos.ProductDto;
import com.project.dtos.ProductReturnDto;
import com.project.model.Product;
import com.project.model.Status;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	// get all product detail of a product by ID
	@Query("SELECT new com.project.dtos.ProductReturnDto(p.id, pd.id, b.name, p.name, p.price, pd.color, pd.size, pd.genderType, "
			+ "pd.star, pd.status, pd.picture1, pd.picture2, pd.picture3, c.id, c.type) "
			+ "FROM Product p JOIN p.productDetails pd JOIN p.catalog c JOIN p.brand b WHERE p.id = :id")
	List<ProductReturnDto> getDetailByProductId(int id);

	// get a specific product detail of a product by detail ID
	@Query("SELECT new com.project.dtos.ProductReturnDto(p.id, pd.id, b.name, p.name, p.price, pd.color, pd.size, pd.genderType, "
			+ "pd.star, pd.status, pd.picture1, pd.picture2, pd.picture3, c.id, c.type) "
			+ "FROM Product p JOIN p.productDetails pd JOIN p.catalog c JOIN p.brand b WHERE p.id = :id AND pd.id = :dtid")
	List<ProductReturnDto> getDetailById(int id, int dtid);

	// get all product detail of a product which by status
	@Query("SELECT new com.project.dtos.ProductReturnDto(p.id, pd.id, b.name, p.name, p.price, pd.color, pd.size, pd.genderType, "
			+ "pd.star, pd.status, pd.picture1, pd.picture2, pd.picture3, c.id, c.type) "
			+ "FROM Product p JOIN p.productDetails pd JOIN p.catalog c JOIN p.brand b WHERE pd.status = :status AND p.id = :id")
	List<ProductReturnDto> getAvailableProductById(Status status, int id);

	// get all product detail of a product by specific size
	@Query("SELECT new com.project.dtos.ProductReturnDto(p.id, pd.id, b.name, p.name, p.price, pd.color, pd.size, pd.genderType, "
			+ "pd.star, pd.status, pd.picture1, pd.picture2, pd.picture3, c.id, c.type) "
			+ "FROM Product p JOIN p.productDetails pd JOIN p.catalog c JOIN p.brand b WHERE pd.size = :size AND p.id = :id")
	List<ProductReturnDto> getProductBySize(int id, int size);

	// get all product detail that still available by specific size
	@Query("SELECT new com.project.dtos.ProductReturnDto(p.id, pd.id, b.name, p.name, p.price, pd.color, pd.size, pd.genderType, "
			+ "pd.star, pd.status, pd.picture1, pd.picture2, pd.picture3, c.id, c.type) "
			+ "FROM Product p JOIN p.productDetails pd JOIN p.catalog c JOIN p.brand b WHERE pd.size = :size AND pd.status = :status AND p.id = :id")
	List<ProductReturnDto> getAvailableProductBySize(int id, int size, Status status);

	// get info of all product with no detail include
	@Query("SELECT new com.project.dtos.ProductDto(p.id, p.name, c.type, p.price, b.name, c.id, b.id) "
			+ "FROM Product p JOIN p.catalog c JOIN p.brand b")
	List<ProductDto> getAll();

	// get all product that belong to a specific brand
	@Query("SELECT new com.project.dtos.ProductDto(p.id, p.name, c.type, p.price, b.name, c.id, b.id) "
			+ "FROM Product p JOIN p.catalog c JOIN p.brand b WHERE b.id = :id")
	List<ProductDto> getByBrand(int id);

	// get all product that belong to a specific brand and catalog
	@Query("SELECT new com.project.dtos.ProductDto(p.id, p.name, c.type, p.price, b.name, c.id, b.id) "
			+ "FROM Product p JOIN p.catalog c JOIN p.brand b WHERE b.id = :brandID AND c.id = :cataId")
	List<ProductDto> getByBrandAndCatalog(int brandID, int cataId);

	// get all size of a product
	@Query(value = "SELECT size FROM product_detail pd join product p on p.id = pd.product_id where p.id = ?", nativeQuery = true)
	Set<Integer> getSize(@Param("id") int id);

	// get all catalog of a brand
	@Query("SELECT new com.project.dtos.CatalogDto(c.type, b.id, c.id) FROM Product p JOIN p.catalog c JOIN p.brand b")
	List<CatalogDto> getCatalogByBrand();

	// get product by id
	@Query("SELECT new com.project.dtos.ProductDto(p.id, p.name, c.type, p.price, b.name, c.id, b.id) "
			+ "FROM Product p JOIN p.catalog c JOIN p.brand b WHERE p.id = :id")
	ProductDto getByProID(int id);

	// get all product details
	@Query("SELECT new com.project.dtos.ProductReturnDto(p.id, pd.id, b.name, p.name, p.price, pd.color, pd.size, pd.genderType, "
			+ "pd.star, pd.status, pd.picture1, pd.picture2, pd.picture3, c.id, c.type) "
			+ "FROM Product p JOIN p.productDetails pd JOIN p.catalog c JOIN p.brand b")
	List<ProductReturnDto> getAllDetail();
	
	// get product details by product detail ID
		@Query("SELECT new com.project.dtos.ProductReturnDto(p.id, pd.id, b.name, p.name, p.price, pd.color, pd.size, pd.genderType, "
				+ "pd.star, pd.status, pd.picture1, pd.picture2, pd.picture3, c.id, c.type) "
				+ "FROM Product p JOIN p.productDetails pd JOIN p.catalog c JOIN p.brand b WHERE pd.id =:id")
		ProductReturnDto getDetailByID(int id);
	
}
