package fail.enormous.jotjot.db

import android.provider.BaseColumns

object NotesDBContract {

        const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "notes_db"

    class NotesItem: BaseColumns {
        companion object {
            const val TABLE_NAME = "notes_item"
            const val COLUMN_NAME_TITLE = "note_title"
            const val COLUMN_NAME_INFO = "note_info"
        }
    }

}