package com.example.system;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Test {
    public interface BaseTest {}
    public interface SayHello extends BaseTest {}

    @JsonView(BaseTest.class)
    @ApiModelProperty("名称")
    private String name;

    @JsonView(SayHello.class)
    @ApiModelProperty("年龄")
    private String age;
}
