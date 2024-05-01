package com.rzyplat.response;

import java.util.List;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.DeviceType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryDeviceTypes {
	private Category category;
	private List<DeviceType> deviceTypes;
}
