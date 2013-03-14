package com.project.util.web.enums;

import org.primefaces.model.SortOrder;

public enum Direction {

    ASC("Crescente"), DESC("Decrescente");
    
    private Direction(String descricao) {

        this.descricao = descricao;
    }

    private String descricao;

    
    public String getDescricao() {
    
        return descricao;
    }
    
    public static Direction getBySortOrder(SortOrder sortOrder){

        Direction direction = null;
        
        switch (sortOrder) {
            case ASCENDING:
                direction = Direction.ASC;
                break;
            
            case DESCENDING:
                direction = Direction.DESC;
                break;
        }
        
        return direction;
    }
}
