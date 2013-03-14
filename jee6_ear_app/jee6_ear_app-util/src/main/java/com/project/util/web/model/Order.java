package com.project.util.web.model;

import com.project.util.web.enums.Direction;

public class Order {

    private String field;

    private String descricao;

    private Direction direction;

    public Order(){
    }
    
    public Order(String field, Direction direction){
        
        this(field, null, direction);
    }
    
    public Order(String field, String descricao) {

        this(field, descricao, Direction.ASC);
    }

    public Order(String field, String descricao, Direction direction) {

        setField(field);
        setDescricao(descricao);
        setDirection(direction);
    }

    public String getField() {

        return field;
    }

    public void setField(String field) {

        this.field = field;
    }

    public String getDescricao() {

        return descricao;
    }

    public void setDescricao(String descricao) {

        this.descricao = descricao;
    }

    public Direction getDirection() {

        return direction;
    }

    public void setDirection(Direction direction) {

        this.direction = direction;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (field == null) {
            if (other.field != null)
                return false;
        } else if (!field.equals(other.field))
            return false;
        return true;
    }
}
