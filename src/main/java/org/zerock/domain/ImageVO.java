package org.zerock.domain;

import java.util.UUID;

import lombok.Data;

@Data
public class ImageVO {

	private UUID image_uuid;
	private String image_url;
	private int product_code;
	private String image_thumbnail;
}
