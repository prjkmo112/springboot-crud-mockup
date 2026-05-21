package com.momo.sparta.mainapi.domains.user.mapper;

import com.momo.sparta.commonmysqldb.entity.User;
import com.momo.sparta.mainapi.domains.user.dto.UserDto;
import com.momo.sparta.mainapi.security.user.CustomUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    UserDto toUserDto(CustomUserDetails customUserDetails);

    User fromUserDto(UserDto userDto);
}
