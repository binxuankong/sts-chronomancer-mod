package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class BoomerangBlade extends AbstractDynamicCard {
    private static final String CARD_ID = BoomerangBlade.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("attack/" + CARD_ID + ".png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 4;
    private boolean played;

    public BoomerangBlade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.played = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
       this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
       this.played = true;
    }

    @Override
    public void onMoveToDiscard() {
        if (this.played) {
            AbstractDungeon.player.discardPile.moveToDeck(this, false);
            this.played = false;
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BoomerangBlade();
    }
}
