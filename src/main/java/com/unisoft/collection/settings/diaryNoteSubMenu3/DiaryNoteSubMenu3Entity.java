package com.unisoft.collection.settings.diaryNoteSubMenu3;
/*
Created by   Islam at 6/30/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.diaryNoteMenu.DiaryNoteMenuEntity;
import com.unisoft.collection.settings.diaryNoteSubMenu1.DiaryNoteSubMenu1Entity;
import com.unisoft.collection.settings.diaryNoteSubMenu2.DiaryNoteSubMenu2Entity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class DiaryNoteSubMenu3Entity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;
    private String remarks;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "diaryNoteMenu_id")
    private DiaryNoteMenuEntity diaryNoteMenu;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "diaryNoteSubMenu1_id")
    private DiaryNoteSubMenu1Entity diaryNoteSubMenu1;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "diaryNoteSubMenu2_id")
    private DiaryNoteSubMenu2Entity diaryNoteSubMenu2;
}
