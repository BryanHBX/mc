package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.edu.timelycourse.mc.common.utils.EntityUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Base model for all entity to persist
 *
 * Created by Marco on 2018/3/31.
 */
@Getter
public abstract class BaseModel implements Serializable
{
    private static final long serialVersionUID = -2488885189931569213L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public void setId (Integer id)
    {
        this.id = (!EntityUtils.isValidEntityId(id)) ? null : id;
    }

    @JsonIgnore
    public abstract boolean isValidInput ();

}