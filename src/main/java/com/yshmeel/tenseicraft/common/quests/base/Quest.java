package com.yshmeel.tenseicraft.common.quests.base;

import java.util.HashMap;

public abstract class Quest implements IQuest {
    protected String id = "";
    protected String name = "";
    protected String description = "";
    protected HashMap<String, Object> criteria = new HashMap<>();

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setDescription(String value) {
        this.description = value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void addCriteria(String key, Object value) {
        this.criteria.put(key, value);
    }

    @Override
    public Object getCriteriaByKey(String key) {
        Object criteriaValue = this.criteria.get(key);

        return (criteriaValue == null ? false : criteriaValue);
    }
}
