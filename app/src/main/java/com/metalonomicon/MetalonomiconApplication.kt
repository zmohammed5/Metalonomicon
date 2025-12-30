package com.metalonomicon

import android.app.Application
import com.metalonomicon.data.database.MetalonomiconDatabase

class MetalonomiconApplication : Application() {
    val database: MetalonomiconDatabase by lazy {
        MetalonomiconDatabase.getDatabase(this)
    }
}
