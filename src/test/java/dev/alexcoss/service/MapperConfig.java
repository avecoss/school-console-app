package dev.alexcoss.service;

import dev.alexcoss.mapper.CourseMapper;
import dev.alexcoss.mapper.GroupMapper;
import dev.alexcoss.mapper.StudentMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public GroupMapper groupMapper() {
        return Mappers.getMapper(GroupMapper.class);
    }

    @Bean
    public CourseMapper courseMapper() {
        return Mappers.getMapper(CourseMapper.class);
    }

    @Bean
    public StudentMapper studentMapper() {
        return Mappers.getMapper(StudentMapper.class);
    }
}
