package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.FatesEdictAction;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class FatesEdict extends AbstractXCostCard {
    private static final String CARD_ID = FatesEdict.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("attack/" + CARD_ID + ".png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public FatesEdict() {
        super(ID, IMG, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.getEffectNum(p);
        if (effect > 0) {
            int total_damage = this.damage * effect;
            int total_block = this.block * effect;
            this.addToBot(new FatesEdictAction(p, m, new DamageInfo(p, total_damage, this.damageTypeForTurn), total_block));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FatesEdict();
    }
}
