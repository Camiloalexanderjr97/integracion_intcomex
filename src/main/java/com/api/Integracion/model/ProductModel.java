package com.api.Integracion.model;

import com.api.Integracion.entity.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class ProductModel {
	  private int id;
	  private  String name;
	  private  String description;
	  private BigDecimal price;
	  private Product category;
	  private String imageUrl;



}
