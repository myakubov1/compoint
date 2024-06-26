package com.example.compoint.mappers;

import com.example.compoint.dtos.BlogRequest;
import com.example.compoint.dtos.BlogResponse;
import com.example.compoint.entity.BlogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogMapper {
    BlogMapper INSTANCE = Mappers.getMapper(BlogMapper.class);

    BlogRequest blogEntityToBlogRequest(BlogEntity blogEntity);
    BlogResponse blogEntityToBlogReposnse(BlogEntity blogEntity);
}
