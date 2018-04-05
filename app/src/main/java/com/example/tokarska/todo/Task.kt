package com.example.tokarska.todo

class Task {
    var content: String? = null
    var id: Int = 0

    constructor(content: String, id: Int) {
        this.content = content
        this.id = id
    }
    constructor(content: String) {
        this.content = content
    }
}