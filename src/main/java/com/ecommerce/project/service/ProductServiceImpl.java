package com.ecommerce.project.service;

import com.ecommerce.project.dto.ProductRequest;
import com.ecommerce.project.dto.ProductResponse;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repository.CategoryRepository;
import com.ecommerce.project.repository.ProductRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, FileService fileService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    }

    @Override
    public ProductRequest addProduct(ProductRequest productRequest, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));


        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();
        for (Product product : products) {
            if (product.getProductName().equals(productRequest.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if (isProductNotPresent) {
            Product product = modelMapper.map(productRequest, Product.class);
            product.setImage("default.png");
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            product.setCategory(category);
            Product saveProduct = productRepository.save(product);
            return modelMapper.map(saveProduct, ProductRequest.class);
        }else {
            throw new APIException("Product Already Exist!!");
        }
    }

    @Override
    public ProductResponse getAllProduct() {
        List<Product> listOfProducts = productRepository.findAll();
        List<ProductRequest> productRequests = listOfProducts.stream()
                .map(p -> modelMapper.map(p, ProductRequest.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productRequests);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
        List<Product> listOfProducts = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductRequest> productRequests = listOfProducts.stream()
                .map(p -> modelMapper.map(p, ProductRequest.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productRequests);
        return productResponse;
    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {
        List<Product> listOfProducts = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductRequest> productRequests = listOfProducts.stream()
                .map(p -> modelMapper.map(p, ProductRequest.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productRequests);
        return productResponse;
    }

    @Override
    public ProductRequest updateProduct(@Valid ProductRequest productRequest, Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        Product product = modelMapper.map(productRequest, Product.class);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setQuantity(product.getQuantity());
        if (product.getDiscount() != null) {
            double specialPrice =
                    product.getPrice() - ((product.getDiscount() / 100.0) * product.getPrice());
            existingProduct.setSpecialPrice(specialPrice);
        } else {
            existingProduct.setSpecialPrice(product.getSpecialPrice());
        }
        Product savedProduct = productRepository.save(existingProduct);
        return modelMapper.map(savedProduct, ProductRequest.class);
    }

    @Override
    public ProductRequest deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductRequest.class);
    }

    @Override
    public ProductRequest updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        String fileName = fileService.uploadImage(path, image);
        productFromDb.setImage(fileName);
        Product updateProduct = productRepository.save(productFromDb);
        return modelMapper.map(updateProduct, ProductRequest.class);
    }

}



