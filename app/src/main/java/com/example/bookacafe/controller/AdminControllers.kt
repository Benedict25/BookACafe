package com.example.bookacafe.controller

import android.database.SQLException
import com.example.bookacafe.model.adminDataDetails.AdminBookDetails
import com.example.bookacafe.model.adminDataDetails.AdminMenuDetails
import com.example.bookacafe.model.adminDataDetails.MemberDummy
import com.example.bookacafe.model.adminDataDetails.TableDummy
import java.sql.ResultSet
import java.sql.Statement

class AdminControllers {
    var con = DatabaseHandler.connect()
    // nitip query SQL
    // query get all user: SELECT a.firstName, a.lastName, sum(if(c.status = 'CANCELED',1,0)) as 'canceledOrder', sum(if(c.status = 'CANCELED',0,1)) as 'fixedOrder' FROM users a JOIN members b ON a.userId = b.memberId JOIN transactions c ON b.memberId = c.memberId GROUP BY b.memberId;
    // query get all seat: SELECT a.tableName, count(b.transactionId) as "totalBooked", count(b.transactionId)*10000 as "tableIncome" FROM tables a JOIN transactions b ON a.tableId = b.tableId GROUP BY a.tableId;
    // query get all food: SELECT a.name, sum(b.menuQuantity) as "totalOrdered", sum(b.menuQuantity)*a.price as "foodIncome", a.imagePath FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId WHERE a.type = "FOOD" GROUP BY a.menuId;
    // query get all drink: SELECT a.name, sum(b.menuQuantity) as "totalOrdered", sum(b.menuQuantity)*a.price as "beverageIncome", a.imagePath FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId WHERE a.type = "BEVERAGE" GROUP BY a.menuId;
    // query get all book: SELECT a.title, sum(b.bookQuantity) as "totalOrdered", a.imagePath FROM books a JOIN detail_transactions b ON a.bookId = b.bookId GROUP BY a.bookId;

    fun getTotalIncome(): Int {
        var income = 0
        val querySeat = "SELECT count(b.transactionId)*10000 as \"tableIncome\" FROM tables a JOIN transactions b ON a.tableId = b.tableId;"
        val queryMenuOrdered = "SELECT sum(b.menuQuantity)*a.price as \"menuIncome\" FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId GROUP BY a.menuId;"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs1: ResultSet = stmt.executeQuery(querySeat)
            val rs2: ResultSet = stmt.executeQuery(queryMenuOrdered)
            while (rs1.next()) {
                income += rs1.getInt("tableIncome")
            }
            while (rs2.next()) {
                income += rs2.getInt("menuIncome")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return income
    }

    fun getUserDetails(): ArrayList<MemberDummy> {
        val members: ArrayList<MemberDummy> = ArrayList()
        val query = "SELECT a.firstName, a.lastName, sum(if(c.status = 'CANCELED',1,0)) as 'canceledOrder', sum(if(c.status = 'CANCELED',0,1)) as 'fixedOrder' FROM users a JOIN members b ON a.userId = b.memberId JOIN transactions c ON b.memberId = c.memberId GROUP BY b.memberId"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val member = MemberDummy(
                    rs.getString("firstName") + " " + rs.getString("lastName"),
                    "Finished Order: " + rs.getString("fixedOrder") + "\nCanceled Order: " + rs.getString("canceledOrder"),
                    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOAAAADhCAMAAADmr0l2AAAAh1BMVEX///8AAAAhISHm5ubKysqjo6Pt7e329vbi4uL5+fmxsbGDg4P39/fOzs67u7vz8/NjY2Nubm64uLjY2NhVVVWPj49ycnJ4eHjb29uamprBwcEzMzNcXFwmJiapqaliYmI8PDwxMTGKiooXFxd+fn5DQ0MRERFNTU2dnZ1QUFBAQEAbGxsjIyP3KlmpAAAMzklEQVR4nO1daXviMA5uC+EI5QghnAUCw8BMO///9+1SatlOnMSWpSQ8u++n0lLbSmzdkl9e+NGJ58FoOTt+fBxnUbRcXZJNcJhv43WvhslZ0eseRrd/r2XYRZttp+l1YtCbJItSyjTcku206RU7YBBG9rQBvlbzZyByOFldEdT94JTETRNQin74F0+cwHLbNBkFGM4JqHsg6jZNTB4x5tiV4DxomiIVw8NbxXr3i+UlCQ7hNw5BMlouThX/8jlvmiyBzqV4lV+Lc9jtvJv/8b03DpOPEjmZtEETiD+KlhcF477NCMP4sPxVNEbTO3X827iuf6OJ48Ofbkcn41CLJgWHmbxjiNS9euHMNN7fpkgcmMSCrxTbLk1vsQltdWqQCzMSGb09Gp5b7Vrcn9wa9gHZIqaHfW74DdXgVhjnuN6M+KDEueN4rU+96ed2J4fA6p1zD7GmfTrPTvxnyDPRMHcOQp6JNPSzgj3gnC1L4o39JXbrPvubzITMGupKn21UoGVSop9RdWeMc3V07l2X/O3cdBLXXBNN9HlqtLy3+swHnllG+u7kmaQI+j6NGGYYavtkz7ZNihBrNvWJnJv2NN3lTD28DfQdRK05cQ5ui7G2iAnl0Bp74eTT5RhqSsYfuoEPNbAwOwQsfC5RR23YSaKdFSJmqmr1Nya92h79T2U5R4oRVQm0ohjQF6qx9uE/nKp9shoO9lD174XvYOr+bE1URLVIPXepyl9aFNlSJaIXp1G5ctM+Zg1rZWEX/DDqVmhDmEBBR1ka2uhWNsK1ZfTpFCJdNeoQraNPXx6KPQyvrabP/wUo9l9LE1kGcoX/3P9bUWBqN25toSimziqNwkBbI9/zUMw4R1aq7O9GzaMqKIJ67PSPMnDuIUbrgKIquxg6UmG/sS2NCDLI7KB3y639i29lRFCkmfVhmsrX3ioF1AxFLbUVZ1IC1hGu8kYIy925/sOSd2VUkBzDytEmN+gb98qoIJ3SNptUJuo+wQF8QB7D39VfloGcevMavCAdD9XxUfhqyr4sQuxg2VXflE6mlpoQZkjVskLzkl9M6lkZFRLLFwMc5qumhZHhS6y8VGOTXhg33bwFsFt6Kr7EFCIb9t/ZghuQ+1Wiz0glmyHfJk6ECnhbYTNKyyAVlGILHbJEyDlMf5NJ33vbkDuyIGSxL/qGfIHEUw9zeXV3rKi3CYxcFN2GPAZiL0VoIu8O4mgVzFPwCkFJoxURw8K0fPJ4KjhazKcQWChpvluntFbrSsptwBWYmv4KgoT0Ba7NhEmQmizwCk2yEHYSpRk/MFOlgvIdwis0+IGlFko44dRIUgaUKZlwHvJCCBKlKFlbaiZJR0o4IWT05JPNYD5CxpYYCcqBMvMNBs3+AXYvYZpkx0iOAYTRHXimWVEArkLCM29dhp3SzdkTY2a8M/CwCV31sZkaEwhrPkAY6G8KFFXCWJmhBKkIhI8VygJ0gwHsCLqZrESEAKFpIYbUwiqgbxCymIOZFDMI1XtgM3HVLz1hLHYsAqEHAZQn9WUJQ4nSWV9VmK2BUv89iUHlr4BoSkvehT5S9QLqneR2hEg3ocS1lvIPEBoVMLOMPQgpTxnPdZCC+tP2x+lnTHCvvYtZKKtZHAmk9MMCyxTCZ8sxy9hMSB0EwrMV+ij4vAgnafINAn8TEWoReyLI8Jao9FXoIE0nFjHt6+NjX0xC6i3sFVBSAFLnE/gPH4cQ1FPapDs3AkmnBkHx8ABvWCZ5ObnQd6KdWwz7EAvCLiU9grly33IQV5yIQ/iwesUkhNVcd0wKaDGCtFZOsWTuH2DDUsc8XQgkjhqCjLprgCDmrdr3OGBkpsUE8pQqMfDdQSF4DHlU3kFQkKccnX4GvptHwjAlKVXTYP0K6YuqI2VklVhaDG0JpD4cclumL3K7EjOyO7YFBGXAObPCRDmKy6xkIUdWOPgo+tKsYWlkYu5KpoElKxz067VUTDnmsVDYUp55RRytC/4Yy3RgVwx3RZSx0geBuxCsXbby/1IPPtusQk4EL6JRG18BSFBEHbn6q0DI4DPIecb83mzDG9iejDnTwjm6ehH9+1grCLqGk7hjLfoSvDOC08ggbVXEmZ6FS+aabiHpFxA44+87F4fJanY8RqNgwp/PL8T7J2TKtrYKEgfh1DuBRHyaIgk7CF3tDXTtp0qyr4ZQsa9AYCsLrfHo/a8Q+Pp/Aj3R7w3W8fiOrsD3p3Ecx+tBZ9pnysKvhcBBcLS4ueDrI2Dg4PIMiiVQc9HexiELYR9Qu2UkFxWCnvYpDpx7+19oPQpCDn6BqkZJ4LupWXYlSPsJSk1GKNuEjvvCMoIqEOrDIib4CbElOuPFKcVJB53VLUI/C7DtqezBnlOGUxYpFbOR9qA4LkSp2o6h+TyIuLlwba/A6URzxB1zK0yg4XaiHc4ZXEIkESyLMolqkLxDwQgC2KwWFeiVcMqBLQbFORROoBD4KUWeWoWT1xYUPmgxVleeGn+tFyXeTfA/LrCX1vJH761vGSyzgXf9GzDzqfoy/fBuXisOvooppHi8yPiPrxvdQ4HJwzdnR2RU3o+zCI545uI4pk9WwXM/CaJmCrGp35D5W1q8cPVbjdAX74kH6nbFA21BFMEr9RECvPeABDAcL9+2x62KBfBZDZyXb72P4pmRv0A/8wYqC74/Cf3DR7wSn8A7fIpURAla+v1JKN4e5zp7EwwJPHwMYohHvigUf+I9h2SXY6rA55aBUfPQiDr6RwQc87NtgS7PhnTRn1cmPqIPoWUxsivQbAYSO34+CysAfQjpZcQ3/mLXIwYQbwx4PFISEvgpzEBaviAFxZmDI4RMJclebkUGpCsTKnmAa55+fpHiBizpp+IHZAqrUERlrUKuXMsNJ+PqCIC7a8FQ7gmHCOcc5aIPyfXgxCipOOJXabsIxCncppJ5SB9HOWb4CMQ4wowl87BHUXyUj0CMawZ4qKbLClGN8o7yEeizGv0AAx/FqPCtIhCcl7qMAdaKScFloy9FLAZ6vGSCOJAej9j2Vo2NMEA4+kAr+8z8AfRRBJtxqhR0AcJ+AxaTM0VgVPdBWez5OxAGYTEZIAoRVtg/4/K8gajZAlM33/MA7PrC5pXFyN2tTANErBcsU4PGAuwHYaQwONVQLxCetElNBzMREX8kDkw8gNDT4EEbxTnEZxGv0KGc1RYIlQOiEOaqdfgzJoT8aV4lHhjLDV5ggXyBVtWIGoohMSfFGPMgy4vsyHnVF0phUSpoDz+rplDSQRYWyrIn9I6i4kAQcSmuJ5fNC1ApF/HujQRXlIsdYoJlGh6c0lbcZuoGSGIp01SkVvl0lT7S+1wq5SBMxFTxygfYfOVFzzKbriVX0toCOEzV3pO23VNVisgAXiX3gG+2/tYzFVIIV/J/mU7Q6nv5dMg6aAtrVj6Mp6m4k30UbbJe5beJO0nx4eT2TmS4j7LXISPkdaWWKqxM3G3x9Z8SMk/VVngrrUGfQFYoKR7WTEP2H36CYyj9QQ5sX/Y4p+/0RAwZP3dJolW6MbX8ijDFCHUy8RQ3GekFKdRQPLKOvSOk8trmW8KU9+BsHCi5Ia1tkqBUEiHCfoq3uqXCQhEQGG6vphCydOzyxdR3gUoK2hvlxTNE6H/J9SEdLAqLeqPvzueJvlJpitYolSy7Xy3bpVMlhdPDcJV6+uu1VZymp9DnVbuq1vu3SFqolaaeLly15Ko1El8NR3r3JlXvFmrJ5eZqxJygtZ6a7krajQGLMy19enNC4l7cGKgPnKh3rspp3hp2tWmNFshCRFoEnrmFXjm07uSEpuqfV47n5g4taYzUMa09uX1DEnGgpeMQe/z04g++vqAl0LYRfQBzelKHT2vnNR0tUWXHoRrrdeSMDVdN0Ktr6Furf0PvZftWo+Y21pPh2IKzmSqsWU0m1DTT4oszfyDTcrkWp2mm9ovZFZ0tt2YP5WcnZFf4e5m0uytrIHj+pc+W1mF2566GZCMxzJBXV+pHL3f/7IaB3QxzDchv9XlN8r1xlsSsrZNPsa03SHLOzZ+GdK1C5/ne6rWH03uGJv0RSXPC+JIfedGEGRqbUmAvnvpNfDbUrO+acnd1jVVL0QTJcobdi6kif99kKsTW3EotTbquBzLemO802DfqQfgvxgV3LbzuL6ElZ+1MzkUJ37/bkMiyLmkYt4s2k7hwx04H22BVUpAwY77SwBrTTUU3i1+36JIcwnCy3Xa3k3l42IyWi6p6oKRV8Z6uw+XsNli0YW/qmB7IKkFPQatCWRK9gKA/5X7TojBWHr0wp4m74G/wDEmq4wS1WdOzs/BsDsPxxoXrXGdBa4KPDpiOD6MqYbA7JmGxoHwOTNfjySFIRqsoimYfH8dZFK1GSRBu40ENW/I/EE2ViEaYFWkAAAAASUVORK5CYII="
                )
                members.add(member)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return members
    }

    fun getBookData(): ArrayList<AdminBookDetails> {
        val books: ArrayList<AdminBookDetails> = ArrayList()
        val query = "SELECT a.title, sum(b.bookQuantity) as \"totalOrdered\", a.imagePath FROM books a JOIN detail_transactions b ON a.bookId = b.bookId GROUP BY a.bookId"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val book = AdminBookDetails(
                    rs.getString("imagePath"),
                    rs.getString("title"),
                    "Total Ordered: " + rs.getString("totalOrdered")
                )
                books.add(book)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return books
    }

    fun getFoodData(): ArrayList<AdminMenuDetails> {
        val foods: ArrayList<AdminMenuDetails> = ArrayList()
        val query = "SELECT a.name, sum(b.menuQuantity) as \"totalOrdered\", sum(b.menuQuantity)*a.price as \"foodIncome\", a.imagePath FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId WHERE a.type = \"FOOD\" GROUP BY a.menuId"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val food = AdminMenuDetails(
                    rs.getString("imagePath"),
                    rs.getString("name"),
                    "Total Ordered: " + rs.getString("totalOrdered") + "\nFood Income: Rp" + rs.getString("foodIncome")
                )
                foods.add(food)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return foods
    }

    fun getBeverageData(): ArrayList<AdminMenuDetails> {
        val beverages: ArrayList<AdminMenuDetails> = ArrayList()
        val query = "SELECT a.name, sum(b.menuQuantity) as \"totalOrdered\", sum(b.menuQuantity)*a.price as \"beverageIncome\", a.imagePath FROM menus a JOIN detail_transactions b ON a.menuId = b.menuId WHERE a.type = \"BEVERAGE\" GROUP BY a.menuId"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val beverage = AdminMenuDetails(
                    rs.getString("imagePath"),
                    rs.getString("name"),
                    "Total Ordered: " + rs.getString("totalOrdered") + "\nBeverage Income: Rp" + rs.getString("beverageIncome")
                )
                beverages.add(beverage)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return beverages
    }

    fun getSeatData():ArrayList<TableDummy> {
        val seats: ArrayList<TableDummy> = ArrayList()
        val query = "SELECT a.tableName, count(b.transactionId) as \"totalBooked\", count(b.transactionId)*10000 as \"tableIncome\" FROM tables a JOIN transactions b ON a.tableId = b.tableId GROUP BY a.tableId"
        try {
            val stmt: Statement = con!!.createStatement()
            val rs: ResultSet = stmt.executeQuery(query)
            while (rs.next()) {
                val seat = TableDummy(
                    rs.getString("tableName"),
                    "Total Booked: " + rs.getString("totalBooked") + "\nTable Income: Rp" + rs.getString("tableIncome")
                )
                seats.add(seat)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return seats
    }
    fun ShowDailyReport() {}
    fun VerifyOrder() {}
    fun VerifyPayment() {}
}