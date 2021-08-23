package com.windfally.uploader.user.mapper;

import com.windfally.uploader.upload.dto.UserDto;
import com.windfally.uploader.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.address.city",target = "city")
    @Mapping(source = "user.address.district",target = "district")
    UserDto toUserDto(User user);
}
