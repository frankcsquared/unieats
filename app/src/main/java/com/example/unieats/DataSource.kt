package com.example.unieats

import com.example.unieats.models.Food

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<Food>{
            val list = ArrayList<Food>()
            list.add(
                Food(
                    "burger!",
                    "https://www.seriouseats.com/recipes/images/2015/07/20150702-sous-vide-hamburger-anova-primary-1500x1125.jpg",
                    10
                )
            )
            list.add(
                Food(
                    "borger",
                    "https://i.redd.it/nzutxc39dbc11.png",
                    10000
                )
            )

            list.add(
                Food(
                    "sandwich",
                    "https://wiki.teamfortress.com/w/images/thumb/9/95/Sandvich.png/250px-Sandvich.png",
                    0
                )
            )
            list.add(
                Food(
                    "potato",
                    "https://www.alimentarium.org/en/system/files/thumbnails/image/AL027-01_pomme_de_terre_0.jpg",
                    1
                )
            )
            list.add(
                Food(
                    "baked potato",
                    "https://food.fnr.sndimg.com/content/dam/images/food/fullset/2011/7/26/1/EA1A02_the-baked-potato_s4x3.jpg.rend.hgtvcom.826.620.suffix/1371599862583.jpeg",
                    2
                )
            )
            list.add(
                Food(
                    "fork",
                    "https://cdn.imgbin.com/18/9/7/imgbin-fork-QtfSU5qP16VvhWTzeRhP5zX9Y.jpg",
                    6
                )
            )
            list.add(
                Food(
                    "chocolate",
                    "https://www.hersheys.com/content/dam/smartlabelproductsimage/kitkat/00034000002467-0010.png",
                    100
                )
            )
            list.add(
                Food(
                    "coffee",
                    "https://i.pinimg.com/564x/2a/95/5f/2a955f6136bc5fe7065f13d8eb3a580a.jpg",
                    101
                )
            )

            return list
        }
    }
}