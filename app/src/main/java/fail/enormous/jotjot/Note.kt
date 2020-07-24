package fail.enormous.jotjot

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import fail.enormous.jotjot.db.NotesDBContract

@Entity(tableName = NotesDBContract.NotesItem.TABLE_NAME)
class Note() {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    var noteId: Long? = null

    @ColumnInfo(name = NotesDBContract.NotesItem.COLUMN_NAME_TITLE)
    var noteTitle: String? = null

    @ColumnInfo(name = NotesDBContract.NotesItem.COLUMN_NAME_INFO)
    var noteInfo: String? = null

    @Ignore
    constructor(noteTitle: String?, noteInfo: String?): this() {
        this.noteTitle = noteTitle
        this.noteInfo = noteInfo
    }

    constructor(taskId:Long, noteTitle: String?, noteInfo: String?) : this(noteTitle, noteInfo) {
        this.noteId = noteId
    }
}