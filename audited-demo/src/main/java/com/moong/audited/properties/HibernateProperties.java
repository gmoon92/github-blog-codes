package com.moong.audited.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
public class HibernateProperties {

    @Value("${hibernate.show_sql}")
    private String showSql;


}
