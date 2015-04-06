package models.daos.slick

import play.api.db.slick.Config.driver.simple._

object DBTableDefinitions {

  case class DBUser (
    userID: String,
    firstName: Option[String],
    lastName: Option[String],
    fullName: Option[String],
    email: Option[String],
    avatarURL: Option[String]
  )

  class Users(tag: Tag) extends Table[DBUser](tag, "user") {
    def id = column[String]("userID", O.PrimaryKey)
    def firstName = column[Option[String]]("firstName")
    def lastName = column[Option[String]]("lastName")
    def fullName = column[Option[String]]("fullName")
    def email = column[Option[String]]("email")
    def avatarURL = column[Option[String]]("avatarURL")
    def * = (id, firstName, lastName, fullName, email, avatarURL) <> (DBUser.tupled, DBUser.unapply)
  }

  case class DBLoginInfo (
    id: Option[Long],
    providerID: String,
    providerKey: String
  )

  class LoginInfos(tag: Tag) extends Table[DBLoginInfo](tag, "logininfo") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def providerID = column[String]("providerID")
    def providerKey = column[String]("providerKey")
    def * = (id.?, providerID, providerKey) <> (DBLoginInfo.tupled, DBLoginInfo.unapply)
  }

  case class DBUserLoginInfo (userID: String, loginInfoId: Long)

  class UserLoginInfos(tag: Tag) extends Table[DBUserLoginInfo](tag, "userlogininfo") {
    def userID = column[String]("userID", O.NotNull)
    def loginInfoId = column[Long]("loginInfoId", O.NotNull)
    def * = (userID, loginInfoId) <> (DBUserLoginInfo.tupled, DBUserLoginInfo.unapply)
  }

  case class DBOAuth2Info (
    id: Option[Long],
    accessToken: String,
    tokenType: Option[String],
    expiresIn: Option[Int],
    refreshToken: Option[String],
    loginInfoId: Long
  )

  class OAuth2Infos(tag: Tag) extends Table[DBOAuth2Info](tag, "oauth2info") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def accessToken = column[String]("accesstoken")
    def tokenType = column[Option[String]]("tokentype")
    def expiresIn = column[Option[Int]]("expiresin")
    def refreshToken = column[Option[String]]("refreshtoken")
    def loginInfoId = column[Long]("logininfoid")
    def * = (id.?, accessToken, tokenType, expiresIn, refreshToken, loginInfoId) <> (DBOAuth2Info.tupled, DBOAuth2Info.unapply)
  }
  
  val slickUsers = TableQuery[Users]
  val slickUserLoginInfos = TableQuery[UserLoginInfos]
  val slickLoginInfos = TableQuery[LoginInfos]
  val slickOAuth2Infos = TableQuery[OAuth2Infos]
}
