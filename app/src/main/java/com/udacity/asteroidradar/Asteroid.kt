package com.udacity.asteroidradar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize @Entity("the_asteroid")
data class Asteroid(
                    @PrimaryKey(autoGenerate = true)
                    @ColumnInfo("the_id")
                    val id: Long,
                    val codename: String,
                    @ColumnInfo("the_date")
                    val closeApproachDate: String,
                    val absoluteMagnitude: Double,
                    val estimatedDiameter: Double,
                    val relativeVelocity: Double,
                    val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable