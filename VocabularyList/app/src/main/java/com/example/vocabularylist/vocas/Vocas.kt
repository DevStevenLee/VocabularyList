package com.example.vocabularylist.vocas

import com.example.vocabularylist.R
import com.example.vocabularylist.add_new_vocabulary.AddNewVocabulary
import com.example.vocabularylist.database.BookmarkDatabaseHandler
import com.example.vocabularylist.database.CustomVocaDatabaseHandler
import com.example.vocabularylist.main.MainActivity

class Vocas {
    val bookmarkDatabaseHandler = BookmarkDatabaseHandler(MainActivity.mContext!!)
    val customDatabaseHandler = CustomVocaDatabaseHandler(MainActivity.mContext!!)

    fun getAnimalVocas() : ArrayList<VocaModel>{
        val animal = ArrayList<VocaModel>()

        animal.add(VocaModel(0, "곰", R.drawable.animal_bear, "", false))
        animal.add(VocaModel(0, "낙타", R.drawable.animal_camel, "", false))
        animal.add(VocaModel(0, "토끼", R.drawable.animal_rabbit,  "",false))
        animal.add(VocaModel(0, "병아리", R.drawable.animal_chick, "", false))
        animal.add(VocaModel(0, "고양이", R.drawable.animal_cat, "", false))
        animal.add(VocaModel(0, "개", R.drawable.animal_dog, "", false))
        animal.add(VocaModel(0, "코끼리", R.drawable.animal_elephant, "", false))
        animal.add(VocaModel(0, "기린", R.drawable.animal_giraffe, "", false))
        animal.add(VocaModel(0, "사자", R.drawable.animal_lion, "", false))
        animal.add(VocaModel(0, "양", R.drawable.animal_sheep, "", false))
        animal.add(VocaModel(0, "닭", R.drawable.animal_chicken, "", false))
        animal.add(VocaModel(0, "소", R.drawable.animal_cow, "", false))
        animal.add(VocaModel(0, "젖소", R.drawable.animal_cow2, "", false))
        animal.add(VocaModel(0, "악어", R.drawable.animal_crocodile, "", false))
        animal.add(VocaModel(0, "사슴", R.drawable.animal_deer, "", false))
        animal.add(VocaModel(0, "돌고래", R.drawable.animal_dolphin, "", false))
        animal.add(VocaModel(0, "비둘기", R.drawable.animal_dove, "", false))
        animal.add(VocaModel(0, "오리", R.drawable.animal_duck, "", false))
        animal.add(VocaModel(0, "독수리", R.drawable.animal_eagle, "", false))
        animal.add(VocaModel(0, "여우", R.drawable.animal_fox, "", false))
        animal.add(VocaModel(0, "개구리", R.drawable.animal_frog, "", false))
        animal.add(VocaModel(0, "고릴라", R.drawable.animal_gorilla, "", false))
        animal.add(VocaModel(0, "햄스터", R.drawable.animal_hamster, "", false))
        animal.add(VocaModel(0, "말", R.drawable.animal_horse, "", false))
        animal.add(VocaModel(0, "표범", R.drawable.animal_leopard, "", false))
        animal.add(VocaModel(0, "원숭이", R.drawable.animal_monkey, "", false))
        animal.add(VocaModel(0, "쥐", R.drawable.animal_mouse, "", false))
        animal.add(VocaModel(0, "펭귄", R.drawable.animal_penguin, "", false))
        animal.add(VocaModel(0, "돼지", R.drawable.animal_pig, "", false))
        animal.add(VocaModel(0, "라쿤", R.drawable.animal_raccoon, "", false))
        animal.add(VocaModel(0, "상어", R.drawable.animal_shark, "", false))
        animal.add(VocaModel(0, "뱀", R.drawable.animal_snake, "", false))
        animal.add(VocaModel(0, "호랑이", R.drawable.animal_tiger, "", false))
        animal.add(VocaModel(0, "거북이", R.drawable.animal_turtle, "", false))
        animal.add(VocaModel(0, "늑대", R.drawable.animal_wolf, "", false))

        return animal
    }

    fun getFlagVocas() : ArrayList<VocaModel>{
        val flag = ArrayList<VocaModel>()

        flag.add(VocaModel(0, "대한민국", R.drawable.flag_korea, "", false))
        flag.add(VocaModel(0, "베트남", R.drawable.flag_vietnam, "", false))
        flag.add(VocaModel(0, "미국", R.drawable.flag_usa, "", false))
        flag.add(VocaModel(0, "영국", R.drawable.flag_uk, "", false))
        flag.add(VocaModel(0, "태국", R.drawable.flag_thailand, "", false))
        flag.add(VocaModel(0, "스위스", R.drawable.flag_switzerland, "", false))
        flag.add(VocaModel(0, "스페인", R.drawable.flag_spain, "", false))
        flag.add(VocaModel(0, "러시아", R.drawable.flag_russia, "", false))
        flag.add(VocaModel(0, "포르투갈", R.drawable.flag_portugal, "", false))
        flag.add(VocaModel(0, "필리핀", R.drawable.flag_philippines, "", false))
        flag.add(VocaModel(0, "북한", R.drawable.flag_northkorea, "", false))
        flag.add(VocaModel(0, "나이지리아", R.drawable.flag_nigeria, "", false))
        flag.add(VocaModel(0, "뉴질랜드", R.drawable.flag_newzealand, "", false))
        flag.add(VocaModel(0, "멕시코", R.drawable.flag_mexico, "", false))
        flag.add(VocaModel(0, "케냐", R.drawable.flag_kenya, "", false))
        flag.add(VocaModel(0, "일본", R.drawable.flag_japan, "", false))
        flag.add(VocaModel(0, "이탈리아", R.drawable.flag_italy, "", false))
        flag.add(VocaModel(0, "이스라엘", R.drawable.flag_israel, "", false))
        flag.add(VocaModel(0, "인도", R.drawable.flag_india, "", false))
        flag.add(VocaModel(0, "독일", R.drawable.flag_germany, "", false))
        flag.add(VocaModel(0, "프랑스", R.drawable.flag_france, "", false))
        flag.add(VocaModel(0, "중국", R.drawable.flag_china, "", false))
        flag.add(VocaModel(0, "캐나다", R.drawable.flag_canada, "", false))
        flag.add(VocaModel(0, "브라질", R.drawable.flag_brazil, "", false))
        flag.add(VocaModel(0, "벨기에", R.drawable.flag_belgium, "", false))
        flag.add(VocaModel(0, "호주", R.drawable.flag_australia, "", false))
        flag.add(VocaModel(0, "아르헨티나", R.drawable.flag_argentina, "", false))

        return flag
    }

    fun getBodyVocas() : ArrayList<VocaModel>{
        val body = ArrayList<VocaModel>()

        body.add(VocaModel(0, "머리카락", R.drawable.body_hair, "", false))
        body.add(VocaModel(0, "손", R.drawable.body_hand, "", false))
        body.add(VocaModel(0, "발", R.drawable.body_foot, "", false))
        body.add(VocaModel(0, "심장", R.drawable.body_heart, "", false))
        body.add(VocaModel(0, "얼굴", R.drawable.body_face, "", false))
        body.add(VocaModel(0, "무릎", R.drawable.body_knee, "", false))
        body.add(VocaModel(0, "눈썹", R.drawable.body_eyebrow, "", false))
        body.add(VocaModel(0, "다리", R.drawable.body_legs, "", false))
        body.add(VocaModel(0, "눈", R.drawable.body_eye, "", false))
        body.add(VocaModel(0, "입", R.drawable.body_mouth, "", false))
        body.add(VocaModel(0, "코", R.drawable.body_nose, "", false))
        body.add(VocaModel(0, "목", R.drawable.body_neck, "", false))
        body.add(VocaModel(0, "어깨", R.drawable.body_shoulder, "", false))
        body.add(VocaModel(0, "팔", R.drawable.body_arm, "", false))
        body.add(VocaModel(0, "복부", R.drawable.body_abdomen, "", false))
        body.add(VocaModel(0, "등", R.drawable.body_back, "", false))

        return body
    }

    fun getCustomVocas() : ArrayList<VocaModel>{
        return customDatabaseHandler.getCustomVocaList()
    }

    fun getBookmarkVocas() : ArrayList<VocaModel>{
        return bookmarkDatabaseHandler.getBookmarkVocaList()
    }
}